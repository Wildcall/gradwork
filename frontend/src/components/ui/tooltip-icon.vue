<template>
  <v-tooltip bottom>
    <template v-slot:activator="{ on, attrs }">
      <v-icon
          small
          class="mr-5"
          @click="$emit('click')"
          v-bind="attrs"
          v-on="on"
      >
        {{ icon }}
      </v-icon>
    </template>
    <span><slot></slot></span>
  </v-tooltip>
</template>

<script>
export default {
  name: "tooltip-icon",

  props: [
    'icon'
  ]
}
</script>