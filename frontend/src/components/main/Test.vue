<template>
    <div class="wrapper">

        <h1>Test</h1>

        <div class="link-wrapper">
            <router-link to="/image_test">/image_test</router-link>
            <br><br>
            <router-link to="/position_test">/position_test</router-link>
        </div>

        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        name: "Test"
    }
</script>

<style scoped>
    .wrapper {
        text-align: center;
        margin: 20px auto;
    }

    .link-wrapper{
        margin-top:20px;
        margin-bottom:20px;
    }
</style>