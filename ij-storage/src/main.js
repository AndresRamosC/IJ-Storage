import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Panel from 'primevue/panel';
import Menubar from 'primevue/menubar';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import Textarea from 'primevue/textarea';
import Message from 'primevue/message';

import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
Vue.config.productionTip = false;
Vue.component('DataTable',DataTable);
Vue.component('Column',Column);
Vue.component('Panel',Panel);
Vue.component('Menubar',Menubar);
Vue.component('Dialog',Dialog);
Vue.component('InputText',InputText);
Vue.component('Button',Button);
Vue.component('Textarea',Textarea);
Vue.component('Message',Message);
new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
