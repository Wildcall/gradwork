<template>
  <v-card>
    <v-card-title
    class="justify-center">
      <v-subheader>
        <slot></slot>
      </v-subheader>
      <v-progress-linear
          active
          indeterminate
          absolute
          bottom
          color="blue"
      />
    </v-card-title>
  </v-card>
</template>

<script>
export default {
  name: "loading-bar",
}
</script>