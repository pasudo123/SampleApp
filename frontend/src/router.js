import Vue from 'vue'
import Router from 'vue-router'

import Main from './components/main/Main.vue'
import Comment from './components/comment/Comment.vue'

import Test from './components/main/Test.vue'
import ImageTest from './components/main/ImageTest.vue'
import PositionTest from './components/main/PositionTest.vue'
import ServerSentEventsTest from './components/main/ServerSentEventsTest.vue'

Vue.use(Router);

export default new Router({

    mode: 'history',
    base: process.env.BASE_URL,

    routes: [

        {
            path: '/',
            name: 'main',
            component: Main
        },
        {
            path: '/comment',
            name: 'comment',
            component: Comment
        },
        {
            path: '/test',
            name: 'test',
            component: Test,
            children: [
                {
                    path: '/image_test',
                    name: 'image-test',
                    component: ImageTest
                },
                {
                    path: '/position_test',
                    name: 'position-test',
                    component: PositionTest
                },
                {
                    path: '/sse_test',
                    name: 'sse-test',
                    component: ServerSentEventsTest
                }

            ]
        },

    ]
})
