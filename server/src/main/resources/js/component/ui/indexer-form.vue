<template>
  <v-card v-if="this.indexer">
    <v-form ref="form"
            v-model="valid"
            lazy-validation
            :disabled="disabled"
    >
      <v-card-title>
        <slot/>
      </v-card-title>
      <v-card-text>
        <v-text-field v-model="newIndexer.presetName"
                      label="Название"
                      :rules="rules.presetNameRule"
                      type="text"
                      required
        >
          <template v-slot:append>
            <v-tooltip
                bottom
            >
              <template v-slot:activator="{ on }">
                <v-icon v-on="on">
                  mdi-help-circle-outline
                </v-icon>
              </template>
              {{ help.presetName }}
            </v-tooltip>
          </template>
        </v-text-field>

        <v-text-field v-model="newIndexer.description"
                      label="Описание"
                      :rules="rules.descriptionRule"
                      type="text"
                      required
        >
          <template v-slot:append>
            <v-tooltip
                bottom
            >
              <template v-slot:activator="{ on }">
                <v-icon v-on="on">
                  mdi-help-circle-outline
                </v-icon>
              </template>
              {{ help.description }}
            </v-tooltip>
          </template>
        </v-text-field>

        <v-card elevation="5" class="mb-5">
          <v-form ref="selectorForm"
                  v-model="selectorValid"
                  lazy-validation
                  :disabled="disabled"
          >
            <v-card-title>
              Добавить новый тег
            </v-card-title>
            <v-card-text>
              <v-row>
                <v-col
                    cols="12"
                    md="6"
                >
                  <v-text-field v-model="selector"
                                label="Html-тег"
                                :rules="rules.selectorRule"
                                type="text"
                                required
                  >
                    <template v-slot:prepend>
                      <v-tooltip
                          bottom
                      >
                        <template v-slot:activator="{ on }">
                          <v-icon v-on="on">
                            mdi-help-circle-outline
                          </v-icon>
                        </template>
                        {{ help.selector }}
                      </v-tooltip>
                    </template>
                  </v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    md="6"
                >
                  <v-text-field v-model="weight"
                                label="Весовой коэффициент"
                                :rules="rules.weightRule"
                                type="number"
                                required
                  >
                    <template v-slot:prepend>
                      <v-tooltip
                          bottom
                      >
                        <template v-slot:activator="{ on }">
                          <v-icon v-on="on">
                            mdi-help-circle-outline
                          </v-icon>
                        </template>
                        {{ help.weight }}
                      </v-tooltip>
                    </template>
                  </v-text-field>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions v-if="!disabled">
              <v-spacer/>
              <v-btn :disabled="!selectorValid"
                     @click="addSelector"
                     text
              >
                Добавить
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>

        <v-card elevation="5" class="mb-5">
          <v-card-title>
            Текущие теги
          </v-card-title>
          <v-card-text>
            <v-simple-table>
              <template v-slot:default>
                <thead style="background-color: #f3f3f3">
                <tr>
                  <th style="color: black">
                    Html-тег
                  </th>
                  <th style="color: black">
                    Весовой коэффициент
                  </th>
                  <th style="color: black"
                      class="text-center"
                      v-if="!disabled"
                  >
                    Удалить
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="(item, i) in newIndexer.selectorWeight"
                    :key="i"
                >
                  <td>{{ item.selector }}</td>
                  <td>{{ item.weight }}</td>
                  <td v-if="!disabled"
                      class="text-center">
                    <v-icon @click="removeSelector(item)">
                      mdi-cancel
                    </v-icon>
                  </td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-card-text>
      <v-card-actions v-if="!disabled">
        <v-btn @click="remove"
               v-if="deleteBtn"
               color="red lighten-2"
               text
        >
          {{ deleteBtnText }}
        </v-btn>
        <v-spacer/>
        <v-btn @click="submit"
               :disabled="!valid"
               text
        >
          {{ submitBtnText }}
        </v-btn>
        <v-btn @click="cancel"
               text
        >
          Отмена
        </v-btn>
      </v-card-actions>
    </v-form>
  </v-card>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "indexer-form",

  data() {
    return {
      valid: true,
      selectorValid: true,
      newIndexer: {
        presetName: '',
        description: '',
        selectorWeight: []
      },
      rules: {
        presetNameRule: [
          v => !!v || 'Не может быть пустым',
          v => (v && v.length <= 30) || 'Имя должно быть короче 30 символов',
          v => this.availablePresetName(v) || 'Такое имя уже используется'
        ],
        descriptionRule: [
          v => !!v || 'Не может быть пустым',
        ],
        selectorRule: [
          v => !!v || 'Не может быть пустым',
          v => this.availableSelector(v) || 'Такой тег уже добавлен'
        ],
        weightRule: [
          v => !!v || 'Не может быть пустым',
          v => Number(v) >= 0 && Number(v) <= 1 || 'Должно быть в диапазоне (0, 1)  ',
        ],
      },
      help: {
        presetName: 'Название для шаблона',
        description: 'Краткое описание шаблона',
        selector: 'Html тег, в котором будет поиск леммы',
        weight: 'Весовой коэффициент который будет использоваться для расчета релевантности встречи слова в данном селекторе, больше -> выше релевантность',
      },
      selector: '',
      weight: 0,
    }
  },

  props: {
    indexer: null,
    deleteBtn: {
      type: Boolean,
      default: false
    },
    deleteBtnText: {
      type: String,
      default: 'Удалить'
    },
    submitBtnText: {
      type: String,
      default: 'Сохранить'
    },
    disabled: {
      type: Boolean,
      default: true,
      required: true
    }
  },

  computed: {
    ...mapGetters({
      indexers: 'settings/getIndexers',
    })
  },

  methods: {
    setValue(indexer) {
      if (indexer){
        this.newIndexer.presetName = indexer.presetName
        this.newIndexer.description = indexer.description
        this.newIndexer.selectorWeight = []
        for (const [key, value] of Object.entries(indexer.selectorWeight)) {
          this.newIndexer.selectorWeight.push({selector: String(key), weight: Number(value)})
        }
      }
    },

    cancel() {
      this.$refs.form.resetValidation()
      this.setValue(this.indexer)
      this.$emit('cancel')
    },

    submit() {
      this.$refs.form.validate()
      if (this.newIndexer.presetName !== '' && this.newIndexer.description !== '' && this.newIndexer.selectorWeight.length !== 0) {
        this.$emit('submit', this.getValue())
      }
    },

    remove() {
      this.$emit('remove', this.indexer)
    },

    getValue() {
      const tmp = {}
      this.newIndexer.selectorWeight.forEach(obj => {
        tmp[String(obj.selector)] = Number(obj.weight)
      })
      return {
        id: this.indexer.id,
        preset: this.indexer.preset,
        presetName: this.newIndexer.presetName,
        description: this.newIndexer.description,
        selectorWeight: tmp,
      }
    },

    availablePresetName(name) {
      if (name.toLowerCase() === this.newIndexer.presetName.toLowerCase())
        return true
      return this.indexers.findIndex(obj => obj.presetName.toLowerCase() === name.toLowerCase()) === -1;
    },

    availableSelector(selector) {
      return this.newIndexer.selectorWeight.findIndex(obj => obj.selector.toLowerCase() === selector.toLowerCase()) === -1;
    },

    addSelector() {
      this.$refs.selectorForm.validate()
      if (this.selector !== '' && this.weight !== '') {
        this.newIndexer.selectorWeight.push({selector: this.selector, weight: Number(this.weight)})
        this.selector = ''
        this.weight = ''
        this.$refs.selectorForm.resetValidation()
      }
    },

    removeSelector(item) {
      if (this.newIndexer.selectorWeight.length === 1) {
        return
      }
      const index = this.newIndexer.selectorWeight.findIndex(obj => obj.selector === item.selector)
      this.newIndexer.selectorWeight.splice(index, 1)
      this.$refs.selectorForm.resetValidation()
    },
  },

  watch: {
    indexer(newValue) {
      this.setValue(newValue)
    },

    disabled() {
      this.setValue(this.crawler)
    }
  },

  mounted() {
    if (this.indexer)
      this.setValue(this.indexer)
  }
}
</script>