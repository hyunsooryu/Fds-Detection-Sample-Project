
//TODO VUE.JS 활용 해당 renderJson으로 탐색 게시판 만들기

let scenarioBoard = new Vue({

    el : "#app",

    data : {
        title : "이상 거래 탐지 애플리케이션",
        subTitle : "V1.0",
        scenarioList : renderJson,
        nowPage : 0,
        nowListing : 1,
        searchType : "custId",
        searchText : ""
    },

    methods : {
        seeMore(){
            let query = "/detect/list";
            query += "?$listing=" + this.nowListing;
            query += "&$page=" + (this.nowPage + 1);
            if(this.searchText !== ""){
                if(this.searchType == "custId") {
                    query += "&$custId=" + this.searchText;
                }else{
                    query += "&$detectPointCd=" + this.searchText;
                }
            }

            var thiz = this;

            axios({
                url : query,
            }).then(function(response){
                var tmpList = response.data;
                console.log(tmpList);
                tmpList.forEach(function(ele){
                    thiz.detectList.push(ele);
                });
                if(tmpList != null && tmpList.length > 0){
                    thiz.nowPage += 1;
                }else{

                }
            }).catch(function(err){
                console.log(err);
            });
        },

        search(){
            let query = "/detect/list";
            this.searchType = document.getElementById("searchSelect").value;
            this.nowListing = document.getElementById("sortSelect").value;
            this.searchText = document.getElementById("search").value;

            query += "?$listing=" + this.nowListing;
            query += "&$page=0";
            if(this.searchText !== ""){
                if(this.searchType == "custId") {
                    query += "&$custId=" + this.searchText;
                }else{
                    query += "&$detectPointCd=" + this.searchText;
                }
            }
            var thiz = this;
            axios({
                url : query,
            }).then(function(response){
                var tmpList = response.data;
                console.log(tmpList);
                thiz.detectList = [];
                tmpList.forEach(function(ele){
                    thiz.detectList.push(ele);
                });
                if(tmpList != null && tmpList.length > 0) {
                    thiz.nowPage = 0;
                }else{
                    thiz.nowPage = 0;
                }
            }).catch(function(err){
                console.log(err);

            });

        }
    }
})