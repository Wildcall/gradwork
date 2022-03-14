<template>
  <v-card elevation="5" class="ma-5">
    <h1>CRAWLER PAGE</h1>
    <v-card-text
        v-for="i in [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]"
        :key="i">
      <div>
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "Crawler",
}
</script>