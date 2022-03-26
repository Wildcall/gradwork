<template>
  <v-app>
    <NavigationBar/>
    <v-main>
      <router-view></router-view>
    </v-main>
    <v-footer app dark>
    </v-footer>
  </v-app>
</template>

<script>
import NavigationBar from "@/components/ui/NavigationBar";

export default {
  components: {NavigationBar},

  created() {
    let SockJS = require('sockjs-client')
    let sock = new SockJS("http://localhost:8000/ws")
    sock.onopen = function () {
      console.log('open')
    }

    sock.onmessage = function (e) {
      console.log('message ', e.data)
    }

    sock.onclose = function () {
      console.log('close')
    }
  }
}
</script>