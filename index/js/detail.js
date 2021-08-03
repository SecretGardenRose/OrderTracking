

// Api Base url
axios.defaults.baseURL = 'http://localhost:2021/';

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
            
            axios.post('../service/test3.php').then(function (res) {
                if(res.status==200){
                    var datalist=res.data.orders;
                    for(var i=0;i<datalist.length;i++){
                        var item=datalist[i];
                        //find myself order
                        if(item.id==that.id){
                            console.log(item);
                            that.order=item;
                        }
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