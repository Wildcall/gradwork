<template>
  <v-card v-if="this.crawler">
    <v-form ref="form"
            v-model="valid"
            lazy-validation
            :disabled="disabled"
    >
      <v-card-title>
        <slot/>
      </v-card-title>
      <v-card-text>
        <div v-for="(item, i) in fields"
             :key="i">
          <v-textarea
              v-if="item.textArea"
              v-model="item.value"
              :label="item.label"
              :rules="item.rule"
              :type="item.type"
              :disabled="disabled"
              auto-grow
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
                {{ item.help }}
              </v-tooltip>
            </template>
          </v-textarea>
          <v-text-field
              v-else
              v-model="item.value"
              :label="item.label"
              :rules="item.rule"
              :type="item.type"
              :disabled="disabled"
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
                {{ item.help }}
              </v-tooltip>
            </template>
          </v-text-field>
        </div>
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
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
    >
      Изменений нет
      <template v-slot:action="{ attrs }">
        <v-btn
            text
            v-bind="attrs"
            @click="snackbar = false"
        >
          Закрыть
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "crawler-form",

  data() {
    return {
      fields: {
        presetName: {
          textArea: false,
          type: 'text',
          value: '',
          label: 'Название',
          rule: [
            v => !!v || 'Не может быть пустым',
            v => (v && v.length <= 30) || 'Имя должно быть короче 30 символов',
            v => this.availablePresetName(v) || 'Такое имя уже используется'
          ],
          help: 'Название для шаблона'
        },
        description: {
          textArea: false,
          type: 'text',
          value: '',
          label: 'Описание',
          rule: [
            v => !!v || 'Не может быть пустым',
          ],
          help: 'Краткое описание шаблона'
        },
        parallelism: {
          textArea: false,
          type: 'number',
          value: '',
          label: 'Кол-во потоков',
          rule: [
            v => !!v || 'Не может быть пустым',
            v => Number(v) > 0 && Number(v) < 33 || 'Должно быть в диапазоне 1-32',
          ],
          help: 'Количество параллельных поток используемых алгоритмом для обхода сайта'
        },
        timeout: {
          textArea: false,
          type: 'number',
          value: '',
          label: 'Timeout',
          rule: [
            v => !!v || 'Не может быть пустым',
            v => Number(v) > -1 && Number(v) < 15000 || 'Должно быть в диапазоне 0-15000 м.сек',
          ],
          help: 'Время ожидания алгоритмом ответа сервера при неудачной попытке загрузки страницы'
        },
        delay: {
          textArea: false,
          type: 'number',
          value: '',
          label: 'Задержка',
          rule: [
            v => !!v || 'Не может быть пустым',
            v => Number(v) > -1 && Number(v) < 5000 || 'Должно быть в диапазоне 0-5000 м.сек',
          ],
          help: 'Пауза между загрузками новых страниц'
        },
        reconnect: {
          textArea: false,
          type: 'number',
          value: '',
          label: 'Переподключение',
          rule: [
            v => !!v || 'Не может быть пустым',
            v => Number(v) > 0 && Number(v) < 11 || 'Должно быть в диапазоне 1-10',
          ],
          help: 'Количество попыток запроса страницы, прежде чем алгоритм пропустит ее'
        },
        referrer: {
          textArea: false,
          type: 'text',
          value: '',
          label: 'Referrer',
          rule: [
            v => !!v || 'Не может быть пустым',
          ],
          help: 'HTTP заголовок, указывающий с какого адреса был совершен переход'
        },
        userAgent: {
          textArea: true,
          type: 'text',
          value: '',
          label: 'User agent',
          rule: [
            v => !!v || 'Не может быть пустым',
          ],
          help: 'Идентификационная строка клиентского приложения'
        },
      },
      valid: true,
      snackbar: false,
      timeout: 2000,
    }
  },

  props: {
    crawler: null,
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
      crawlers: 'settings/getCrawlers',
    })
  },

  methods: {
    setValue(crawler) {
      if (crawler){
        this.fields.presetName.value = crawler.presetName
        this.fields.description.value = crawler.description
        this.fields.parallelism.value = crawler.parallelism
        this.fields.timeout.value = crawler.timeout
        this.fields.delay.value = crawler.delay
        this.fields.reconnect.value = crawler.reconnect
        this.fields.referrer.value = crawler.referrer
        this.fields.userAgent.value = crawler.userAgent
      }
    },

    cancel() {
      this.$refs.form.resetValidation()
      this.setValue(this.crawler)
      this.$emit('cancel')
    },

    submit() {
      this.$refs.form.validate()
      if (this.fields.presetName.value !== '' && this.fields.description.value !== '') {
        this.compareValue() ? this.$emit('submit', this.getValue()) : this.snackbar = true
      }
    },

    remove() {
      this.$emit('remove', this.crawler)
    },

    getValue() {
      return {
        id: this.crawler.id,
        preset: this.crawler.preset,
        presetName: this.fields.presetName.value,
        description: this.fields.description.value,
        parallelism: this.fields.parallelism.value,
        delay: this.fields.delay.value,
        timeout: this.fields.timeout.value,
        reconnect: this.fields.reconnect.value,
        referrer: this.fields.referrer.value,
        userAgent: this.fields.userAgent.value,
      }
    },

    compareValue() {
      let result = false
      Object.keys(this.crawler).forEach(key => {
        if (key in this.fields) {
          if (this.crawler[key] !== this.fields[key].value)
            result = true
        }
      })
      return result
    },

    availablePresetName(name) {
      if (name.toLowerCase() === this.crawler.presetName.toLowerCase())
        return true
      return this.crawlers.findIndex(obj => obj.presetName.toLowerCase() === name.toLowerCase()) === -1;
    },
  },

  watch: {
    crawler(newValue) {
      this.setValue(newValue)
    },

    disabled() {
      this.setValue(this.crawler)
    }
  },

  mounted() {
    if (this.crawler)
      this.setValue(this.crawler)
  }
}
</script>