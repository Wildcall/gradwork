<template>
  <v-card
      v-if="site"
      flat
  >
    <v-form
        ref="form"
        v-model="valid"
        lazy-validation
    >
      <v-list three-line>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>ID</v-list-item-title>
            <v-list-item-subtitle>{{ site.id }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Адрес сайта</v-list-item-title>
            <v-list-item-subtitle>
              {{ site.path }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Название</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field
                  v-model="siteName"
                  :rules="nameRules"
                  autofocus
                  required
              >
                {{ site.name }}
              </v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Статус</v-list-item-title>
            <v-list-item-subtitle>{{ site.status }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Последнее изменение</v-list-item-title>
            <v-list-item-subtitle>{{ site.statusTime }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

      </v-list>
    <v-card-actions class="justify-end">
      <v-dialog
          v-model="confirm"
          width="500"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              :disabled="!valid || siteName.toLowerCase() === site.name.toLowerCase()"
              @click="update"
              text
          >
            <v-progress-circular
                v-if="loading"
                indeterminate
                size="25"
                color="black"
            />
            <span v-else>
              Сохранить изменения
            </span>
          </v-btn>
          <v-btn
              text
              v-bind="attrs"
              v-on="on"
          >
            Удалить
          </v-btn>
        </template>

        <v-card>
          <v-card-title>
            Подтверждение удаления
          </v-card-title>

          <v-card-text>
            В случае удаления сайта будут удалены все сохраненные страницы сайта, индексы и леммы. Вы уверены что хотите удалить сайт?
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                text
                @click="confirm=false"
            >
              Отмена
            </v-btn>
            <v-btn
                text
                @click="confirmDeleting"
            >
              <v-progress-circular
                  v-if="loading"
                  indeterminate
                  size="25"
                  color="black"
              />
              <span v-else>
                Удалить
              </span>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card-actions>
    </v-form>
  </v-card>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "SiteEdit",

  data() {
    return {
      confirm: false,
      site: null,
      siteName: null,
      valid: false,
      nameRules: [
        v => !!v || 'Не может быть пустым',
        v => (v && v.length <= 10) || 'Имя должно быть короче 10 символов',
        v => this.siteName.toLowerCase() === v.toLowerCase() || '',
        v => this.availableName(v) || 'Имя уже занято'
      ],
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      loading: 'site/getLoading',
      tabs: 'storage/getTabs'
    })
  },

  methods: {
    ...mapActions({
      deleteSite: 'site/deleteSite',
      updateSite: 'site/updateSite',

      storageUpdateTab: 'storage/updateTab',
      storageDeleteTab: 'storage/deleteTab',
    }),

    confirmDeleting() {
      this.deleteSite(Number(this.$route.params.id)).then(()=>{
        this.confirm = false
        this.closeTab()
      })
    },

    update() {
      this.$refs.form.validate()
      const newSite = {
          id: this.site.id,
          name: this.siteName,
          crawlerId: this.site.crawlerId,
          indexerId: this.site.indexerId
      }
      this.updateSite(newSite).then((response) => {
        this.site.name = response.name
        const tab = this.tabs.find(obj => obj.link === this.$route.path)
        tab.title = response.name
        this.storageUpdateTab(tab)
      })
    },

    closeTab() {
      this.storageDeleteTab(this.$route.path)
      this.$router.push({path: '/'})
    },

    availableName(name) {
      if (this.site.name.toLowerCase() !== this.siteName.toLowerCase())
        return this.sites.findIndex(obj => obj.name.toLowerCase() === name.toLowerCase()) === -1;
      return true
    },
  },

  beforeRouteUpdate(to, from, next) {
    this.site = this.sites.find(obj => obj.id === Number(to.params.id))
    this.siteName = this.site.name
    next()
  },

  mounted() {
    this.site = this.sites.find(obj => obj.id === Number(this.$route.params.id))
    this.siteName = this.site.name
  },
}
</script>