

// Api Base url
axios.defaults.baseURL = 'http://localhost:2021/';

new Vue({
    el:"#app",
    data:{
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
                alert("请先邮箱地址和订单编号！");
                return;
            }
            var reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
            // var reg=/[A-z]+[A-z0-9_-]*\@[A-z0-9]+\.[A-z]+/; 
            if(!reg.test(that.query.mail)){
                alert("邮箱格式不正确，请重新输入！");
            }
            
            //5287 lana4gana@gmail.com
            axios.post('../service/test3.php').then(function (res) {
                if(res.status==200){
                    var datalist=res.data.orders;
                    // console.log(datalist);
                    for(var i=0;i<datalist.length;i++){
                        var item=datalist[i];
                        //find myself order
                        if(item.order_number==that.query.order_number && item.email==that.query.mail){
                            console.log(item);
                            that.order=item;
                            location.href="detail.html?id="+item.id;
                        }
                    }
                }else{
                    alert("no data");
                }
            })
        },
        compateDate(t){
            var  now=new Date();
            var now_year=now.getFullYear();
            var now_month=now.getMonth()+1;
            var now_date=now.getDate();

            var create_date=new Date(t);
            var t_year=create_date.getFullYear();
            var t_month=create_date.getMonth()+1;
            var t_date=create_date.getDate();
            if(now_year==t_year && now_month==t_month  && now_date==t_date){
                return  true;
            }
            return false;
        }
    }
})