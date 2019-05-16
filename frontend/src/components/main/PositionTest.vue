<template>
    <div class="test-wrapper">

        <router-link to="/test">테스트 뷰로 가기</router-link>
        <br><br><br>
        <h2>Scroll And Position Test-View</h2>

        <div class="position-wrapper">
            <p>위치 확인</p>

            <p>
                <button @click="moveScroll(1)">스크롤 이동 (1)</button>
                <button @click="moveScroll(2)">스크롤 이동 (2)</button>
                <button @click="moveScroll(3)">스크롤 이동 (3)</button>
                <button @click="moveScroll(4)">스크롤 이동 (4)</button>
                <button @click="moveScroll(5)">스크롤 이동 (5)</button>
            </p>
            <p>
                <button @click="moveScroll(6)">스크롤 이동 (6)</button>
                <button @click="moveScroll(7)">스크롤 이동 (7)</button>
                <button @click="moveScroll(8)">스크롤 이동 (8)</button>
                <button @click="moveScroll(9)">스크롤 이동 (9)</button>
                <button @click="moveScroll(10)">스크롤 이동 (10)</button>
            </p>
        </div>

        <div class="list-wrapper">
            <div v-for="(item, index) in getItems">
                <div class="element" ref="index">
                    <span>{{index}} >> </span>{{item.message}}
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    let intervalColorBlock = null;

    export default {
        name: "PositionTest",
        data() {
            return {
                items: [],
                itemPositions: [],
            }
        },
        computed: {
            getItems() {
                this.setPositions();
                return this.items;
            },
        },
        methods: {
            initItems() {
                let number = 20;

                for (let i = 0; i < number; i++) {
                    this.items[i] = {};
                    this.items[i].number = (i + 1);
                    this.items[i].message = "안녕하세요_" + (i + 1);
                }
            },

            setPositions() {
                // console.log(this.$refs);
                // console.log(this.$refs.index);
            },

            moveScroll(index) {
                // 해당 위치로 스크롤 이동 및 색깔 표시
                const absoluteTop = window.pageYOffset + this.$refs.index[index].getBoundingClientRect().top;

                // 현재 엘리먼트의 스타일 값 획득
                let originBorder = this.$refs.index[index].style.border;

                // 색상 변경
                this.$refs.index[index].style.border = '3px inset #009FFF';

                // 다시 원상복귀
                setTimeout(() => {
                    this.$refs.index[index].style.border= originBorder;
                }, 2000);

                window.scrollTo(0, absoluteTop - 200);
            },
        },
        created() {
            this.initItems();
        },
    }
</script>

<style scoped>

    .test-wrapper {
        width: 800px;
        margin: 50px auto;
        text-align: center;
    }

    .position-wrapper {
        margin-top: 20px;
        padding-top: 20px;
        padding-bottom: 20px;
        border: 1px solid grey;
    }

    .element {
        margin: 25px auto;
        padding-top: 20px;
        padding-bottom: 20px;
        background-color: antiquewhite;
        width: 200px;
        border: 1px solid darkgray;
    }

    button {
        width: 150px;
        padding: 10px;
        border: 1px solid darkslateblue;
        border-radius: 15px;
        color: darkslateblue;
    }

    button:hover {
        background-color: darkslateblue;
        color: white;
    }

</style>