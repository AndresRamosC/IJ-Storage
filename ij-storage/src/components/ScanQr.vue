<template>
  <div>
    <p class="error">{{ error }}</p>
    <Message
      v-for="msg of messages"
      :severity="msg.severity"
      :key="msg.content"
      :life="3000"
      :sticky="false"
    >{{msg.content}}</Message>

    <qrcode-stream :camera="camera" @decode="onDecode" @init="onInit" />
    <Dialog header="Create Component " :modal="true" :closable="false" :visible.sync="displayCreateModal">
      <div class="p-fluid p-formgrid p-grid">
        <div class="p-field p-col-12 p-md-6">
          <label for="id">Id</label>
          <div>
            <InputText id="id" type="text" v-model="componentItem.id" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-6">
          <label for="department">Department</label>
          <div>
            <InputText id="department" type="text" v-model="componentItem.department" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-6">
          <label for="project">project</label>
          <div>
            <InputText id="project" type="text" v-model="componentItem.project" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-6">
          <label for="partNo">Part Number</label>
          <div>
            <InputText id="partNo" type="text" v-model="componentItem.partNo" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-4">
          <label for="minStock">Minimum Stock</label>
          <div>
            <InputText id="minStock" type="number" v-model="componentItem.minStock" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-4">
          <label for="stock">Current Stock</label>
          <div>
            <InputText id="stock" type="number" v-model="componentItem.stock" />
          </div>
        </div>
        <div class="p-field p-col-12 p-md-4">
          <label for="maxStock">Maximum Stock</label>
          <div>
            <InputText id="maxStock" type="number" v-model="componentItem.maxStock" />
          </div>
        </div>

        <div class="p-field p-col-12 p-md-15">
          <label for="description">Description</label>
          <div>
            <Textarea v-model="componentItem.description" :autoResize="true" rows="5" cols="30" />
          </div>
        </div>
      </div>
      <template #footer>
        <Button label="Save" icon="pi pi-check" @click="save" autofocus />
        <Button label="Cancel" icon="pi pi-times" @click="closeCreateModal" class="p-button-text" />
      </template>
    </Dialog>
    <Dialog header="Stock Component " :modal="true" :closable="false" :visible.sync="displayUpdateStockModal">
        <div class="p-fluid">
          <div class="p-field p-grid">
            <label for="stock" class="p-col-12 p-mb-2 p-md-2 p-mb-md-0">Current Stock from Component {{componentItem.partNo}}</label>
            <div class="p-inputgroup">
              <Button icon="pi pi-minus-circle" class="p-button-danger" @click="minusbutton" />
              <InputText
                id="stock"
                type="number"
                v-model="componentItem.stock"
                style="width: 100%"
              />
              <Button icon="pi pi-plus-circle" class="p-button-success" @click="plusbutton" />
            </div>
          </div>
        </div>
        <template #footer>
          <Button label="Save" icon="pi pi-check" @click="updateStock" autofocus />
          <Button
            label="Cancel"
            icon="pi pi-times"
            @click="closeUpdateStockModal"
            class="p-button-text"
          />
        </template>
      </Dialog>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";
import ComponentService from "../service/componentService";
export default {
  components: { QrcodeStream },

  data() {
    return {
      camera: "auto",
      result: "",
      error: "",
      messages: [],
      displayCreateModal: false,
      displayUpdateStockModal: false,
      componentItem: {
        id: null,
        department: null,
        project: null,
        partNo: null,
        description: null,
        stock: null,
        maxStock: null,
        minStock: null
      }
    };
  },
  componentService: null,
  created() {
    this.componentService = new ComponentService();
  },
  methods: {
    onDecode(result) {
      this.result = JSON.parse(result);
      this.getComponentFromQr(this.result);
    },
    async onInit(promise) {
      try {
        await promise;
      } catch (error) {
        if (error.name === "NotAllowedError") {
          this.error = "ERROR: you need to grant camera access permisson";
        } else if (error.name === "NotFoundError") {
          this.error = "ERROR: no camera on this device";
        } else if (error.name === "NotSupportedError") {
          this.error = "ERROR: secure context required (HTTPS, localhost)";
        } else if (error.name === "NotReadableError") {
          this.error = "ERROR: is the camera already in use?";
        } else if (error.name === "OverconstrainedError") {
          this.error = "ERROR: installed cameras are not suitable";
        } else if (error.name === "StreamApiNotSupportedError") {
          this.error = "ERROR: Stream API is not supported in this browser";
        }
      }
    },
    getComponentFromQr(jsonResult) {
      if (
        jsonResult.id !== null &&
        jsonResult.project !== null &&
        jsonResult.department !== null
      ) {
        this.camera= "off";
        this.componentService
          .getComponent(
            jsonResult.id,
            jsonResult.department,
            jsonResult.project
          )
          .then(data => {
            console.log(data);
            this.componentItem.id=jsonResult.id;
            this.componentItem.department=jsonResult.department;
            this.componentItem.project=jsonResult.project;
            this.componentItem.stock = data.data.stock;
            this.componentItem.partNo = data.data.partNo;
            this.showUpdateStockModal();
          })
          .catch(error => {
            console.log(error.response);
            this.componentItem.id=jsonResult.id;
            this.componentItem.department=jsonResult.department;
            this.componentItem.project=jsonResult.project;
            this.componentItem.partNo=jsonResult.partNo;
            this.componentItem.maxStock=0;
            this.componentItem.stock=0;
            this.componentItem.minStock=0;
            this.componentItem.description=jsonResult.description;
            this.showSaveModal();
          });
      }
    },
    save() {
      this.componentService
        .save(this.componentItem)
        .then(data => {
          if (data.status === 201) {
            this.closeCreateModal();
            this.pushMessage("success", "Component Saved");
          }
        })
        .catch(error => {
          this.closeCreateModal();
          console.log(error.response.data);
          this.pushMessage("error", error.response.data);
        });
    },
    updateStock() {
      this.componentService
        .updateComponentStock(
          this.componentItem.id,
          this.componentItem.department,
          this.componentItem.project,
          this.componentItem.stock
        )
        .then(data => {
          if (data.status === 201) {
            this.closeUpdateStockModal();
            this.componentItem = {
              id: null,
              department: null,
              project: null,
              partNo: null,
              description: null,
              stock: null,
              maxStock: null,
              minStock: null
            };
          }
        })
        .catch(error => {
          this.closeUpdateStockModal();
          this.pushMessage("error", error.response.data);
        });
    },
    showSaveModal() {
      this.displayCreateModal = true;
    },
    closeCreateModal() {
      this.componentItem = {
        id: null,
        department: null,
        project: null,
        partNo: null,
        description: null,
        stock: null,
        maxStock: null,
        minStock: null
      };
      this.camera= "auto";
      this.displayCreateModal = false;
    },
    showUpdateStockModal() {
      this.displayUpdateStockModal = true;
    },
    closeUpdateStockModal() {
      this.componentItem = {
        id: null,
        department: null,
        project: null,
        partNo: null,
        description: null,
        stock: null,
        maxStock: null,
        minStock: null
      };
      this.camera= "auto";
      this.displayUpdateStockModal = false;
    },
    minusbutton() {
      this.componentItem.stock = this.componentItem.stock - 1;
    },
    plusbutton() {
      this.componentItem.stock = this.componentItem.stock + 1;
    },
    pushMessage(severity, message) {
      this.messages.push({ severity: severity, content: message });
    }
  }
};
</script>

<style scoped>
.error {
  font-weight: bold;
  color: red;
}
</style>