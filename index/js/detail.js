

// Api Base url
axios.defaults.baseURL = 'http://localhost:2021/';
axios.defaults.withCredentials =true;
new Vue({
    el:"#app",
    data:{
        id:"",
        order:"",
    },
    mounted () {
        this.id=this.getparams("id");
      this.initdata();
    },
    methods: {
        initdata(){
            var that=this;
            
            axios.get('http://localhost:8080/south-fast/mall/mallorder/info/'+that.id).then(function (res) {
                if(res.data.code==0){
                    console.log(res.data.mallOrder);
                    if(res.data.mallOrder==null){
                        alert("no data");
                    }else{
                        that.order=res.data.mallOrder;
                    }
                }else{
                    alert("no data");
                }
            })
        },
        back_home(){
            location.href="index.html";
        },
        getparams(name){
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)
                return  unescape(r[2]); 
            return null;
        }
    }
})