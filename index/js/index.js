// function getByDate(status, querydate, check) {
//     var data = new FormData();
//     data.append('date', querydate);
// data.append('fulfillment', status);
// data.append('check', check);

//     var xhr = new XMLHttpRequest();
//     xhr.open('POST', 'test1.php');
//     xhr.onload = function () {

//             var div = document.getElementById('result_div_id');
//             if (div) {
//                     div.remove();
//             }

//             //Create the element using the createElement method.
//             var myDiv = document.createElement("div");

//             //Set its unique ID.
//             myDiv.id = 'result_div_id';

//             //Add your content to the DIV
//             myDiv.innerHTML = this.response;;

//             //Finally, append the element to the HTML body
//             document.body.appendChild(myDiv);
//     };
//     xhr.send(data);
//     return false;
// }

// Api Base url
axios.defaults.baseURL = 'http://localhost:2021/';

new Vue({
    el:"#app",
    data:{
        order:{
            mail:"",
            order_number:""
        }
    },
    created () {
      console.log("ok");  
      this.submit_order();
    },
    methods: {
        submit_order(){
            var that=this;
            
            axios.post('../service/test3.php').then(function (res) {
                console.log(res);
                if(res.status==200){
                    var datalist=res.data.orders;
                    console.log(datalist);
                    for(var i=0;i<datalist.length;i++){
                        var item=datalist[i];
                        var flag=that.compateDate(item.created_at);
                        console.log(flag);
                        // console.log(now_year2+"-"+now_month2+"-"+now_date2);
                    }
                }else{
                    alert("no data");
                }
                // var datas=res.data
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