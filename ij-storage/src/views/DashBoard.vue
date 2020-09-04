<template>
  <div class="dashboard">
    <h1>IJ-Storage</h1>
    <Message
      v-for="msg of messages"
      :severity="msg.severity"
      :key="msg.content"
      :life="3000"
      :sticky="false"
    >{{msg.content}}</Message>
    <div>
      <Menubar :model="items" />
      <br />
      <DataTable
        class="p-datatable-responsive-demo p-datatable-striped"
        :value="listedComponents"
        :paginator="true"
        :rows="10"
        :selection.sync="selectedComponent"
        :filters="filters"
        selectionMode="single"
        data-key="id"
        paginatorTemplate=" PrevPageLink PageLinks NextPageLink  "
      >
        <template #header>
          <div class="table-header">
            List of components
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText v-model="filters['global']" placeholder="Global Search" />
            </span>
          </div>
        </template>
        <Column field="id" header="Id" sortable>
          <template #body="slotProps">
            <span class="p-column-title">id</span>
            {{slotProps.data.id}}
          </template>
        </Column>
        <Column field="department" header="Department" sortable>
          <template #body="slotProps">
            <span class="p-column-title">Department</span>
            {{slotProps.data.department}}
          </template>
        </Column>
        <Column field="project" header="Project" sortable>
          <template #body="slotProps">
            <span class="p-column-title">Project</span>
            {{slotProps.data.project}}
          </template>
        </Column>
        <Column field="partNo" header="Part Number">
          <template #body="slotProps">
            <span class="p-column-title">Part Number</span>
            {{slotProps.data.partNo}}
          </template>
        </Column>
        <Column field="stock" header="Current Stock">
          <template #body="slotProps">
            <span class="p-column-title">Current Stock</span>
            {{slotProps.data.stock}}
          </template>
        </Column>
      </DataTable>
      <Dialog header="Create Component " :modal="true"  :closable="false" :visible.sync="displayCreateModal">
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
          <Button
            label="Cancel"
            icon="pi pi-times"
            @click="closeCreateModal"
            class="p-button-text"
          />
        </template>
      </Dialog>
      <Dialog header="Edit Component " :modal="true"  :closable="false" :visible.sync="displayUpdateModal">
        <div class="p-fluid p-formgrid p-grid">
          <div class="p-field p-col-12 p-md-12">
            <label for="partNo">Part Number</label>
            <div>
              <InputText id="partNo" type="text" v-model="selectedComponent.partNo" />
            </div>
          </div>

          <div class="p-field p-col-12 p-md-6">
            <label for="maxStock">Maximum Stock</label>
            <div>
              <InputText id="maxStock" type="number" v-model="selectedComponent.maxStock" />
            </div>
          </div>
          <div class="p-field p-col-12 p-md-6">
            <label for="minStock">Minimum Stock</label>
            <div>
              <InputText id="minStock" type="number" v-model="selectedComponent.minStock" />
            </div>
          </div>
          <div class="p-field p-col-12 p-md-12">
            <label for="description">Description</label>
            <div>
              <Textarea
                v-model="selectedComponent.description"
                :autoResize="true"
                rows="5"
                cols="30"
              />
            </div>
          </div>
        </div>
        <template #footer>
          <Button label="Save" icon="pi pi-check" @click="update" autofocus />
          <Button
            label="Cancel"
            icon="pi pi-times"
            @click="closeUpdateModal"
            class="p-button-text"
          />
        </template>
      </Dialog>
      <Dialog header="Stock Component " :modal="true"  :closable="false" :visible.sync="displayUpdateStockModal">
        <div class="p-fluid">
          <div class="p-field p-grid">
            <label for="stock" class="p-col-12 p-mb-2 p-md-2 p-mb-md-0">Current Stock</label>
            <div class="p-inputgroup">
              <Button icon="pi pi-minus-circle" class="p-button-danger" @click="minusbutton" />
              <InputText
                id="stock"
                type="number"
                v-model="selectedComponent.stock"
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
      <Dialog header="Details Component " :modal="true" :closable="false" :visible.sync="displayDetailsModal">
        <div class="p-fluid p-formgrid p-grid">
          <div class="p-field p-col-6 p-md-6">
            <label for="id">Id</label>
            <InputText id="id" type="text" v-model="selectedComponent.id" disabled />
          </div>
          <div class="p-field p-col-6 p-md-6">
            <label for="department">Department</label>
            <InputText id="department" type="text" v-model="selectedComponent.department" disabled />
          </div>
          <div class="p-field p-col-6 p-md-6">
            <label for="project">Project</label>
            <InputText id="project" type="text" v-model="selectedComponent.project" disabled />
          </div>
          <div class="p-field p-col-6 p-md-6">
            <label for="partNo">Part Number</label>
            <InputText id="partNo" type="text" v-model="selectedComponent.partNo" disabled />
          </div>
          <div class="p-field p-col-6 p-md-4">
            <label for="minStock">Minimum Stock</label>
            <InputText id="minStock" type="number" v-model="selectedComponent.minStock" disabled />
          </div>
          <div class="p-field p-col-6 p-md-4">
            <label for="stock">Current Stock</label>
            <InputText id="stock" type="number" v-model="selectedComponent.stock" disabled />
          </div>
          <div class="p-field p-col-6 p-md-4">
            <label for="maxStock">Maximum Stock</label>
            <InputText id="maxStock" type="number" v-model="selectedComponent.maxStock" disabled />
          </div>

          <div class="p-field p-col-12">
            <label for="description">description</label>
            <Textarea
              v-model="selectedComponent.description"
              :autoResize="true"
              rows="5"
              cols="30"
              disabled
            />
          </div>
        </div>
        <div>
          <Panel header="Movement Logs">
            <DataTable
              class="p-datatable-responsive-demo p-datatable-striped"
              :value="selectedComponentMovements"
              :rows="10"
            >
              <Column field="timeStamp" header="Date" sortable>
                <template #body="slotProps">
                  <span class="p-column-title">Date</span>
                  {{slotProps.data.timeStamp}}
                </template>
              </Column>
              <Column field="oldStock" header="Previus Stock">
                <template #body="slotProps">
                  <span class="p-column-title">Previus Stock</span>
                  {{slotProps.data.oldStock}}
                </template>
              </Column>
              <Column field="newStock" header="New Stock">
                <template #body="slotProps">
                  <span class="p-column-title">New Stock</span>
                  {{slotProps.data.newStock}}
                </template>
              </Column>
              <Column field="movement_log_type" header="Movement Type">
                <template #body="slotProps">
                  <span class="p-column-title">Movement Type</span>
                  {{slotProps.data.movement_log_type}}
                </template>
              </Column>
            </DataTable>
          </Panel>
        </div>

        <template #footer>
          <Button
            label="Exit"
            icon="pi pi-times"
            @click="closeDetailsModal"
            class="p-button-text"
            autofocus
          />
        </template>
      </Dialog>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import ComponentService from "../service/componentService";
export default {
  name: "Dashboard",
  data() {
    return {
      listedComponents: null,
      filters: {},
      messages: [],
      componentItem: {
        id: null,
        department: null,
        project: null,
        partNo: null,
        description: null,
        stock: null,
        maxStock: null,
        minStock: null
      },
      selectedComponent: {
        id: null,
        department: null,
        project: null,
        partNo: null,
        description: null,
        stock: null,
        maxStock: null,
        minStock: null
      },
      mutableComponent: null,
      selectedComponentMovements: null,
      items: [
        {
          label: "New",
          icon: "pi pi-fw pi-plus",
          command: () => {
            this.showSaveModal();
          }
        },
        {
          label: "Edit",
          icon: "pi pi-fw pi-pencil",
          command: () => {
            this.showUpdateModal();
          }
        },
        {
          label: "Details",
          icon: "pi pi-fw pi-align-justify",
          command: () => {
            this.showDetailsModal();
          }
        },
        {
          label: "Stock",
          icon: "pi pi-fw pi-briefcase",
          command: () => {
            this.showUpdateStockModal();
          }
        },
        {
          label: "Refresh",
          icon: "pi pi-fw pi-refresh",
          command: () => {
            this.getAll();
          }
        }
      ],
      displayCreateModal: false,
      displayUpdateModal: false,
      displayDetailsModal: false,
      displayUpdateStockModal: false
    };
  },
  componentService: null,
  created() {
    this.componentService = new ComponentService();
  },
  mounted() {
    this.getAll();
  },
  methods: {
    showSaveModal() {
      this.displayCreateModal = true;
    },
    showUpdateStockModal() {
      if (this.selectedComponent.id !== null) {
        this.mutableComponent = this.selectedComponent;
        this.componentService
          .getComponent(
            this.mutableComponent.id,
            this.mutableComponent.department,
            this.mutableComponent.project
          )
          .then(data => {
            if (data.status === 200) {
              this.mutableComponent.stock = data.data.stock;
            }
            this.displayUpdateStockModal = true;
          });
      } else {
        this.pushMessage("warn", "Please select an Item");
      }
    },
    showUpdateModal() {
      if (this.selectedComponent.id !== null) {
        this.mutableComponent = this.selectedComponent;
        this.componentService
          .getComponent(
            this.mutableComponent.id,
            this.mutableComponent.department,
            this.mutableComponent.project
          )
          .then(data => {
            if (data.status === 200) {
              this.mutableComponent.description = data.data.description;
              this.mutableComponent.maxStock = data.data.maxStock;
              this.mutableComponent.minStock = data.data.minStock;
            }
            this.displayUpdateModal = true;
          });
      } else {
        this.pushMessage("warn", "Please select an Item");
      }
    },
    showDetailsModal() {
      if (this.selectedComponent.id !== null) {
        this.mutableComponent = this.selectedComponent;
        this.componentService
          .getComponent(
            this.mutableComponent.id,
            this.mutableComponent.department,
            this.mutableComponent.project
          )
          .then(data => {
            if (data.status === 200) {
              this.mutableComponent.description = data.data.description;
              this.mutableComponent.maxStock = data.data.maxStock;
              this.mutableComponent.minStock = data.data.minStock;
              this.mutableComponent.stock = data.data.stock;
              this.selectedComponentMovements = data.data.movementLogs;
            }
            this.displayDetailsModal = true;
          });
      } else {
        this.pushMessage("warn", "Please select an Item");
      }
    },

    getAll() {
      this.componentService.getAll().then(data => {
        this.listedComponents = data.data;
      });
    },
    save() {
      this.componentService
        .save(this.componentItem)
        .then(data => {
          if (data.status === 201) {
            this.displayCreateModal = false;
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
            this.getAll();
          }
        })
        .catch(error => {
          this.displayCreateModal = false;
          this.pushMessage("error", error.response.data);
        });
    },
    update() {
      var updateComponentPayload = {
        partNo: null,
        description: null,
        maxStock: null,
        minStock: null
      };
      this.mutableComponent = this.selectedComponent;
      updateComponentPayload.partNo = this.mutableComponent.partNo;
      updateComponentPayload.description = this.mutableComponent.description;
      updateComponentPayload.maxStock = this.mutableComponent.maxStock;
      updateComponentPayload.minStock = this.mutableComponent.minStock;
      this.componentService
        .updateComponent(
          this.mutableComponent.id,
          this.mutableComponent.department,
          this.mutableComponent.project,
          updateComponentPayload
        )
        .then(data => {
          if (data.status === 201) {
            this.displayUpdateModal = false;
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
            this.getAll();
          }
        })
        .catch(error => {
          this.displayUpdateModal = false;
          this.pushMessage("error", error.response.data);
        });
    },
    updateStock() {
      this.mutableComponent = this.selectedComponent;
      this.componentService
        .updateComponentStock(
          this.mutableComponent.id,
          this.mutableComponent.department,
          this.mutableComponent.project,
          this.mutableComponent.stock
        )
        .then(data => {
          if (data.status === 201) {
            this.displayUpdateStockModal = false;
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
            this.getAll();
          }
        })
        .catch(error => {
          this.displayUpdateStockModal = false;
          this.pushMessage("error", error.response.data);
        });
    },
    closeCreateModal() {
      this.displayCreateModal = false;
    },
    closeUpdateModal() {
      this.getAll();
      this.displayUpdateModal = false;
    },
    closeUpdateStockModal() {
      this.getAll();
      this.displayUpdateStockModal = false;
    },
    closeDetailsModal() {
      this.displayDetailsModal = false;
    },
    minusbutton() {
      this.mutableComponent.stock = this.mutableComponent.stock - 1;
    },
    plusbutton() {
      this.mutableComponent.stock = this.mutableComponent.stock + 1;
    },
    pushMessage(severity, message) {
      this.messages.push({ severity: severity, content: message });
    }
  },
  components: {}
};
</script>


<style lang="scss" scoped>
.p-datatable-responsive-demo .p-datatable-tbody > tr > td .p-column-title {
  display: none;
}

@media screen and (max-width: 40em) {
  /deep/ .p-datatable {
    &.p-datatable-responsive-demo {
      .p-datatable-thead > tr > th,
      .p-datatable-tfoot > tr > td {
        display: none !important;
      }

      .p-datatable-tbody > tr > td {
        text-align: left;
        display: block;
        border: 0 none !important;
        width: 100% !important;
        float: left;
        clear: left;

        .p-column-title {
          padding: 0.4rem;
          min-width: 30%;
          display: inline-block;
          margin: -0.4em 1em -0.4em -0.4rem;
          font-weight: bold;
        }
      }
    }
  }
}
</style>

