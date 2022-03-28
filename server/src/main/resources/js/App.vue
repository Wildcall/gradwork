<template>
    <v-app>
      <navigation-bar/>
      <v-main>
        <router-view></router-view>
      </v-main>
      <v-footer app dark>
      </v-footer>
    </v-app>
</template>

<script>
import NavigationBar from "./component/ui/NavigationBar.vue";

export default {
  components: {NavigationBar}
}
</script>