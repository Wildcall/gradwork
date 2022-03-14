<template>
  <v-card flat class="mx-auto">
    <v-card-title>
      {{ title }}
    </v-card-title>
    <v-card-subtitle>
      {{ subtext }}
    </v-card-subtitle>

    <v-divider class="mt-5 mb-5"/>

    <v-list dense>
      <v-list-item-group mandatory>

        <v-list-item @click="$emit('action',items[0].action)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[0].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="$emit('action',items[1].action)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[1].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-group no-action>
          <template v-slot:activator>
            <v-list-item-title>
              {{ items[2].group }}
            </v-list-item-title>
          </template>
          <v-list-group sub-group>
            <template v-slot:activator>
              <v-list-item-title>
                {{ items[2].subgroup }}
              </v-list-item-title>
            </template>
            <v-list-item @click="$emit('action',items[2].action)">
              <v-list-item-content>
                <v-list-item-title>
                  {{ items[2].title }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="$emit('action',items[3].action)">
              <v-list-item-content>
                <v-list-item-title>
                  {{ items[3].title }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
          <v-list-group sub-group>
            <template v-slot:activator>
              <v-list-item-title>
                {{ items[4].subgroup }}
              </v-list-item-title>
            </template>
            <v-list-item @click="$emit('action',items[4].action)">
              <v-list-item-content>
                <v-list-item-title>
                  {{ items[4].title }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="$emit('action',items[5].action)">
              <v-list-item-content>
                <v-list-item-title>
                  {{ items[5].title }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
        </v-list-group>
        <v-list-item @click="$emit('action',items[6].action)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[6].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="$emit('action',items[7].action)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[7].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider class="mt-5 mb-5"/>

        <v-list-item @click="$emit('action', items[8].action)">
          <v-list-item-content>
            <v-list-item-title>
              {{ items[8].title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "site-menu",

  data() {
    return {
      tab: null
    }
  },

  props: {
    title: String,
    subtext: String,
    items: []
  }
}
</script>