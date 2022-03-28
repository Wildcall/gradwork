<template>
  <div>
    <v-tooltip bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-icon
            v-bind="attrs"
            v-on="on"
        >
          {{ icon }}
        </v-icon>
      </template>
      <span>{{ title }}</span>
    </v-tooltip>
    <span v-if="!icon" >{{ title }}</span>
  </div>
</template>

<script>
export default {
  name: "tooltip-icon",

  props: {
    icon: String,
    title: String,
  }
}
</script>