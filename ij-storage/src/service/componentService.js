
import axios from 'axios';

export default class ComponentService{
    
    host="192.168.1.68"; //<-change for your ip Adress
    port=":9080";
    method="https://"; 

    getAll(){
        
        console.log(process.env);
        return axios.get(this.method+this.host+this.port+'/components')
    }
    save(component){
        return axios.post(this.method+this.host+this.port+'/components',component)
    }
    getComponent(id,department,project){
        return axios.get(this.method+this.host+this.port+'/components/'+id+'/'+department+'/'+project)
    }
    updateComponent(id,department,project,component){
        return axios.put(this.method+this.host+this.port+'/components/'+id+'/'+department+'/'+project,component)
    }
    updateComponentStock(id,department,project,newStock){
        return axios.put(this.method+this.host+this.port+'/components/'+id+'/'+department+'/'+project+'/'+newStock)
    }
}