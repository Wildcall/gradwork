<template>
  <div>
    <SiteEdit
        v-if="siteEdit"
        :site="site"
        v-on:close="close"
        v-on:delete="delSite"
        v-on:update="updSite"
    />
    <SiteStat
        v-else-if="siteStat"
        :site="site"
        v-on:close="close"
    />
    <SiteError
        v-else-if="siteError"
        :errors="errors"
        v-on:close="close"
    />
    <SiteList
        v-else
        :sites="sites"
        :loading="loading"
        v-on:open-stat="openSiteStat"
        v-on:open-edit="openSiteEdit"
        v-on:open-error="openSiteErrors"
        v-on:add-site="addNewSite"
    />
  </div>
</template>

<script>
import SiteList from "@/components/site/SiteList";
import {mapActions, mapGetters} from "vuex";
import SiteEdit from "@/components/site/SiteEdit";
import SiteStat from "@/components/site/SiteStat";
import SiteError from "@/components/site/SiteError";

export default {
  name: "Sites",

  components: {SiteError, SiteStat, SiteEdit, SiteList},

  data() {
    return {
      siteEdit: false,
      siteStat: false,
      siteError: false
    }
  },

  computed: {
    ...mapGetters({
      sites: 'site/getSites',
      site: 'site/getSite',
      loading: 'site/getLoading',
      errors: 'site/getErrors'
    })
  },

  methods: {
    ...mapActions({
      createSite: 'site/createSite',
      readSites: 'site/readSites',
      readSite: 'site/readSite',
      readSiteErrors: 'site/readSiteErrors',
      updateSite: 'site/updateSite',
      deleteSite: 'site/deleteSite'
    }),
    openSiteStat(id) {
      this.readSite(id)
      this.siteStat = true
    },
    openSiteErrors(id) {
      this.readSiteErrors(id)
      this.siteError = true
    },
    openSiteEdit(id) {
      this.readSite(id)
      this.siteEdit = true
    },
    close() {
      this.siteStat = false
      this.siteError = false
      this.siteEdit = false
    },
    addNewSite(item) {
      this.createSite(item)
    },
    delSite(site) {
      this.close()
      this.deleteSite(site)
    },
    updSite(item) {
      console.log(item)
      this.updateSite(item)
    }
  },

  mounted() {
    this.readSites()
  }

}
</script>