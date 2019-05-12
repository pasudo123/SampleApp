import Vue from 'vue'
import Router from 'vue-router'

import Main from './components/main/Main.vue'
import Comment from './components/comment/Comment.vue'

Vue.use(Router)

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
    }
  ]
})