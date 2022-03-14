<template>
  <v-dialog v-model="dialog" persistent max-width="600px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          v-bind="attrs"
          v-on="on"
          class="ml-5"
          light
      >
        Добавить сайт
      </v-btn>
    </template>
    <v-form
        ref="form"
        v-model="valid"
        lazy-validation
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Добавление сайта</span>
        </v-card-title>
        <v-card-text>
          <v-col cols="12">
            <v-text-field
                v-model="site.path"
                label="Адрес сайта"
                :rules="pathRules"
                autofocus
                hint="https://example.com"
                required
            />
          </v-col>
          <v-col cols="12">
            <v-text-field
                v-model="site.name"
                label="Название сайта"
                :rules="nameRules"
                required
            />
          </v-col>
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn
              text
              @click="close"
          >
            Закрыть
          </v-btn>
          <v-btn
              :disabled="!valid"
              @click="submit"
              text
          >
            <v-progress-circular
                v-if="loading"
                indeterminate
                size="25"
                color="black"
            />
            <span v-else>
              Добавить
            </span>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "SiteNew",

  data() {
    return {
      dialog: false,
      valid: true,
      site: {
        path: '',
        name: ''
      },
      pathRules: [
        v => !!v || 'Не может быть пустым',
        v => /^(http(s?):\/\/)?((([a-z\d]([a-z\d-]*[a-z\d])*)\.)+[a-z]{2,}|((\d{1,3}\.){3}\d{1,3}))(:\d+)?(\/[-a-z\d%_.~+]*)*(\?[;&a-z\d%_.~+=-]*)?(#[-a-z\d_]*)?/.test(v) || 'Должно иметь вид https://example.com',
        v => this.availablePath(v) || 'Такой сайт уже существует'
      ],
      nameRules: [
        v => !!v || 'Не может быть пустым',
        v => (v && v.length <= 10) || 'Имя должно быть короче 10 символов',
        v => this.availableName(v) || 'Имя уже занято'
      ],
    }
  },

  computed: {
    ...mapGetters({
      loading: 'site/getLoading',
      sites: 'site/getSites'
    })
  },

  methods: {
    ...mapActions({
      createSite: 'site/createSite',
    }),

    submit() {
      this.$refs.form.validate()
      const newSite = {
        name: this.site.name,
        path: this.site.path
      }
      this.createSite(newSite).then((response) => {
        if (response){
          this.close()
          this.$emit('open-tab',{id: response.id, name: response.name})
        }
      })
    },

    close() {
      this.$refs.form.resetValidation()
      this.site.name = ''
      this.site.path = ''
      this.dialog = false
    },

    availableName(name) {
      return this.sites.findIndex(obj => obj.name.toLowerCase() === name.toLowerCase()) === -1;
    },

    availablePath(path) {
      return this.sites.findIndex(obj => obj.path.toLowerCase() === path.toLowerCase()) === -1;
    }
  },
}
</script>