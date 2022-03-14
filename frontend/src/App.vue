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
}
</script>