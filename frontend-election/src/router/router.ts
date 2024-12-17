import { createRouter, createWebHistory} from 'vue-router'

import HomePage from "@/components/HomePage.vue";
import articlePage from "@/components/ArticlePage.vue";
import login from '../components/Login.vue';
import register from '../components/UserRegister.vue';
import accountPage from '../components/AccountPage.vue';
import VotingGuidePage from '../components/VotingGuidePage.vue';
import VotingQuizPage from '../components/VotingQuizPage.vue';
import PartyPage from "@/components/PartyPage.vue";
import partij from "@/components/ShowParties.vue";
import shadowElections from "../components/ShadowElections.vue";
import article from "@/components/Article.vue";
import chatRoom from "@/components/ChatRoom.vue";
import ChatRooms from "@/components/ChatRooms.vue";
import ResultsElections from "../components/ResultsElections.vue";
import CompareElections from "../components/CompareElections.vue";

//footer
import contact from "../components/footer/UserContact.vue";
import nieuwsbrief from "../components/footer/NieuwsBrief.vue";
import PoliticsInfo from "../components/footer/PoliticsInfo.vue";
import AvFAQVa from "../components/footer/AvFAQVa.vue";
import AboutUs from "../components/footer/AboutUs.vue";
import Feedback from "../components/footer/FeedBack.vue";







const routes = [
  { path: '/', component: HomePage },
  { path: '/articlepage', component: articlePage },
  {path: '/article:articleId',
    name: 'Article',
    component: article,
    props: (route:any) => ({ articleId: Number(route.params.articleId) }), // Convert to a number
  },
  { path: '/login', component: login },
  { path: '/register', component: register },
  { path: '/account', component: accountPage },
  { path: '/VotingGuidePage', component: VotingGuidePage },
  { path: '/VotingQuizPage', component: VotingQuizPage },
  { path: '/chatrooms', component: ChatRooms },
  { path: '/chatroom/:theme',
    name: 'ChatRoom',
    component: chatRoom,
    props: (route:any) => ({ theme: route.params.theme })
  },
  { path: '/ResultsElections', component: ResultsElections },
  { path: '/CompareElections', component: CompareElections },



  { path: '/shadowElections', component: shadowElections },

  {
    path: '/partij/:partyId',
    name: 'partij',
    component: partij,
    props: true,
  },

  { path: '/PartyPage', component: PartyPage },



  //footer
  { path: '/contact', component: contact },
  { path: '/Nieuwsbrief', component: nieuwsbrief },
  { path: '/PoliticsInfo', component: PoliticsInfo },
  { path: '/Feedback', component: Feedback },
  { path: '/AvFAQVa', component: AvFAQVa },
  { path: '/aboutUs', component: AboutUs },



]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
    }
})

export default router