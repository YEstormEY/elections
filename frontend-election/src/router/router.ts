import { createRouter, createWebHistory} from 'vue-router'

import HomePage from '../components/HomePage.vue'
import articlePage from "@/components/ArticlePage.vue";
import login from '../components/Login.vue'
import register from '../components/Register.vue'
import accountPage from '../components/AccountPage.vue'
import Partijen from '../components/Partijen.vue'
import VotingGuidePage from '../components/VotingGuidePage.vue'
import VotingQuizPage from '../components/VotingQuizPage.vue'

//footer
import contact from '../components/footer/contact.vue'
import nieuwsbrief from '../components/footer/nieuwsbrief.vue'
import shadowElections from "@/components/ShadowElections.vue";
import PoliticsInfo from '@/components/footer/PoliticsInfo.vue';
import Feedback from '@/components/footer/Feedback.vue';
import AvFAQVa from '@/components/footer/AvFAQVa.vue';
import aboutUs from '@/components/footer/AboutUs.vue';
import article from "@/components/Article.vue";
import chatRoom from "@/components/ChatRoom.vue";
import ChatRooms from "@/components/ChatRooms.vue";
import ResultsElections from '@/components/ResultsElections.vue'





const routes = [
  { path: '/', component: HomePage },
  { path: '/articlepage', component: articlePage },
  {path: '/article:articleId',
    name: 'Article',
    component: article,
    props: route => ({ articleId: Number(route.params.articleId) }), // Convert to a number
  },
  { path: '/login', component: login },
  { path: '/register', component: register },
  { path: '/account', component: accountPage },
  { path: '/Partijen', component: Partijen },
  { path: '/VotingGuidePage', component: VotingGuidePage },
  { path: '/VotingQuizPage', component: VotingQuizPage },
  { path: '/chatrooms', component: ChatRooms },
  { path: '/chatroom/:theme',
    name: 'ChatRoom',
    component: chatRoom,
    props: route => ({ theme: route.params.theme })
  },
  { path: '/ResultsElections', component: ResultsElections },


  { path: '/shadowElections', component: shadowElections },


  //footer
  { path: '/contact', component: contact },
  { path: '/Nieuwsbrief', component: nieuwsbrief },
  { path: '/PoliticsInfo', component: PoliticsInfo },
  { path: '/Feedback', component: Feedback },
  { path: '/AvFAQVa', component: AvFAQVa },
  { path: '/aboutUs', component: aboutUs },



]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router