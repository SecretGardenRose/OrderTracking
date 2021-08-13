// Api Base url
axios.defaults.baseURL = 'https://www.sgrcredit.com/';
// axios.defaults.baseURL = 'http://localhost:8080/';


new Vue({
    el:"#app",
    data:{
        title:"Track Your Order",
        default_img:"http://www.sgrcredit.com/south-fast/imgs/1e986a84-6bef-4ad3-8937-31a9afba77a5.jpg",
        showdetail:false,
        id:"",
        order:"",
        query:{
            mail:"",
            order_number:""
        }
    },
    created () {
    },
    methods: {
        submit_order(){
            var that=this;

            if(that.query.mail=="" || that.query.order_number==""){
                alert("Please email address and order number first.");
                return;
            }
            var reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
            if(!reg.test(that.query.mail)){
                alert("The mailbox format is incorrect, please re-enter.");
            }
            var params=new FormData();
            params.append("email",that.query.mail);
            params.append("orderNo",that.query.order_number);
            axios.post("south-fast/mall/mallorder/importOrder",params).then(function(res){
                // console.log(res);
                if(res.data.code==0){
                    console.log(res.data.data);
                    if(res.data.data==null){
                        alert("Order cannot be found.");
                        return;
                    }else{
                        that.id=res.data.data.id;
                        that.showdetail=true;
                        that.title="Your Order Detail";
                        that.initdata();

                    }
                }else{
                    alert("Order cannot be found.");
                }
            })
        },
        initdata(){
            var that=this;
            
            axios.get('south-fast/mall/mallorder/info/'+that.id).then(function (res) {
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
            //写入访问记录
            var params={orderNumber:that.query.mail,userEmail:that.query.order_number};
            axios.post('south-fast/mall/accessrecord/save',params).then(function (res) {
                if(res.data.code==0){
                    console.log("ok");
                }
            })
        },
        back_home(){
            this.showdetail=false;
            that.title="Track Your Order";
        },
    }
})