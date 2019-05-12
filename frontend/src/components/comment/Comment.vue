<template>
    <v-layout class="layout">
        <v-list three-line>
            <template v-for="(item, index) in this.items">
                <v-list-tile
                        :key="item.title"
                        avatar
                        @click="toggleValue(index)"
                >
                    <v-list-tile-avatar>
                        <img :src="item.avatar">
                    </v-list-tile-avatar>

                    <v-list-tile-content>
                        <v-list-tile-title v-html="item.title"></v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile v-if="toggle[index] == 2">
                    <v-list-tile-content>
                        보인다보인다보인다.
                    </v-list-tile-content>
                </v-list-tile>
            </template>
        </v-list>
    </v-layout>
</template>

<script>
    import Vue from "vue";

    export default {
        name: "Comment",
        data() {
            return {
                toggle: [],
                items: [
                    {
                        val: 0,
                        avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
                        title: 'Brunch this weekend?',
                        subtitle: "<span class='text--primary'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?"
                    },
                    {
                        val: 1,
                        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
                        title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
                        subtitle: "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend."
                    },
                ]

            }
        },
        methods: {
            toggleValue(index) {
                if (this.toggle[index] == 1) {
                    Vue.set(this.toggle, index, 2)
                } else {
                    Vue.set(this.toggle, index, 1)
                }

                console.log(this.toggle)
            }
        },

        created() {
            for (let i = 0; i < 2; i++) {
                this.toggle[i] = 1
            }
        },

        updated: function () {
            this.$nextTick(function () {
                console.log(this.toggle)
            })
        }
    }
</script>

<style scoped>

    .layout {
        margin: 0 auto;
        width: 500px;
        border: 1px solid red;
    }

</style>