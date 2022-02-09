<template>
  <v-app>
    <Navtabs/>
    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
//import Navbar from "@/components/Navbar";
import Navtabs from "@/components/Navtabs";

export default {
  components: {Navtabs}
}
</script>
