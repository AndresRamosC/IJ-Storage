<template>
  <div class="home">
    <ScanQr msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// @ is an alias to /src
import ScanQr from "@/components/ScanQr.vue";

export default {
  name: "Home",
  components: {
    ScanQr
  }
};
</script>
