(ns culture.facts
  "Country-level regional-culture catalog for Mali (MLI) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"MLI"
   [{:culture/id "mli.dish.tigadegue"
     :culture/name "Peanut stew"
     :culture/name-local "Tigadèguèna"
     :culture/country "MLI"
     :culture/kind :dish
     :culture/summary "In Mali the Mandinka and Bambara peoples' tigadèguèna variant of peanut stew is traditionally more watery than Senegalese maafe and prepared with unrefined shea butter, served with Malian fufu (tuwo)."
     :culture/url "https://en.wikipedia.org/wiki/Peanut_stew"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.dish.jollof-rice"
     :culture/name "Jollof rice"
     :culture/name-local "Zaamè"
     :culture/country "MLI"
     :culture/kind :dish
     :culture/summary "Rice dish known regionally by many names; in Mali it is called zaamè in Bamanankan, and food historian James C. McCann proposed it spread with the Mali Empire's Djula tradespeople."
     :culture/url "https://en.wikipedia.org/wiki/Jollof_rice"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.dish.fonde-monoo"
     :culture/name "Fonde/monoo"
     :culture/country "MLI"
     :culture/kind :dish
     :culture/summary "A Malian millet porridge that is also commonly eaten in Senegal, part of a Malian food culture heavily based on cereal grains."
     :culture/url "https://en.wikipedia.org/wiki/Malian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.beverage.malian-tea"
     :culture/name "Malian tea"
     :culture/country "MLI"
     :culture/kind :beverage
     :culture/summary "Sweet, sugary tea that is a staple of Malian breakfasts alongside millet-based porridges and breads."
     :culture/url "https://en.wikipedia.org/wiki/Malian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.craft.bogolanfini"
     :culture/name "Bògòlanfini"
     :culture/name-local "Bogolan"
     :culture/country "MLI"
     :culture/kind :craft
     :culture/summary "Handcrafted Malian mud cloth: cotton fabric woven in narrow strips and dyed using fermented mud and iron-rich riverbed clay, worn for cultural identity."
     :culture/url "https://en.wikipedia.org/wiki/B%C3%B2g%C3%B2lanfini"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.festival.festival-au-desert"
     :culture/name "Festival au Désert"
     :culture/country "MLI"
     :culture/kind :festival
     :culture/summary "Annual music festival founded by Manny Ansar that ran 2001-2012 across sites including Tin Essako, Tessalit, Essakane and near Timbuktu, showcasing Tuareg and world music before being halted by the Northern Mali conflict."
     :culture/url "https://en.wikipedia.org/wiki/Festival_in_the_Desert"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.heritage.timbuktu"
     :culture/name "Timbuktu"
     :culture/country "MLI"
     :culture/kind :heritage
     :culture/summary "Historic centre of Timbuktu was selected by the UNESCO World Heritage Committee for inscription on the World Heritage List in December 1988."
     :culture/url "https://en.wikipedia.org/wiki/Timbuktu"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mli.heritage.djenne"
     :culture/name "Great Mosque of Djenné"
     :culture/country "MLI"
     :culture/kind :heritage
     :culture/summary "The largest adobe brick building in the world, located in Djenné, Mali, and designated a UNESCO World Heritage Site in 1988."
     :culture/url "https://en.wikipedia.org/wiki/Great_Mosque_of_Djenn%C3%A9"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-mli culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "MLI"))
                 " MLI entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
