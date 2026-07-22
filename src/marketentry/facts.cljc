(ns marketentry.facts
  "Mali market-entry catalog.

  Every fact under \"MLI\" below is grounded ONLY in a verified research
  dossier gathered by web search against Mali government/regulator
  domains (armds.ml / dgmp.gouv.ml / sgg-mali.ml / rccm.ohada.org). No
  regulatory claim, requirement, or institutional detail beyond that
  dossier is stated here -- where the dossier did not surface a
  concrete citation (e.g. a specific NIF-issuance decree number), the
  gap is left unfilled rather than invented; see the
  `:corporate-number-legal-basis` comment below.

  \"USA\"/\"CIV\"/\"SEN\" are the same generic reference-jurisdiction
  entries already merged and load-bearing in sibling
  cloud-itonami-iso3166-* repos (ago/sen), reused here verbatim only so
  `coverage` has more than one covered jurisdiction to report on --
  they are NOT Mali-specific claims.")

(def catalog
  {"MLI" {:name "Mali"
          :owner-authority "ARMDS (Autorité de Régulation des Marchés Publics et des Délégations de Service Public) / DGMP-DSP e-procurement"
          ;; ARMDS's own founding-law citation (armds.ml/qui-sommes-nous)
          ;; plus the concretely-located procurement-code text (the 2015
          ;; edition archived on the sgg-mali.ml government domain). ARMDS's
          ;; own site additionally references a 2025 recodification aligned
          ;; to UEMOA community directives, but no direct URL for that 2025
          ;; text was verified for this catalog -- noted, not cited as if
          ;; verified.
          :legal-basis "Loi n°08-023 du 23 juillet 2008, modifiée par Loi n°2011-030 du 24 juin 2011 (ARMDS) ; Code des marchés publics, texte 2015 (archivé par le Secrétariat Général du Gouvernement du Mali) -- ARMDS mentionne une recodification 2025 alignée UEMOA, non vérifiée par URL directe pour ce catalogue"
          :national-spec "DGMP-DSP e-procurement + RCCM (registre du commerce, cadre OHADA) + NIF"
          :provenance "https://www.armds.ml/qui-sommes-nous/"
          :required-evidence ["RCCM registration record (OHADA)" "NIF record (DGI)" "DGMP-DSP e-procurement registration record" "Authorized-representative record"]
          :rep-owner-authority "commercial-court greffier (RCCM) / contracting authorities"
          ;; Mali ratified OHADA (signed 17 October 1993, ratified 7
          ;; February 1995); RCCM registration within one month of
          ;; formation is required of all companies except société en
          ;; participation, INCLUDING foreign commercial companies with a
          ;; registered office in an OHADA member state and branches of
          ;; foreign companies operating in Mali.
          :rep-legal-basis "OHADA RCCM registration required within one month of formation (all companies except société en participation), including foreign commercial companies with a registered office in an OHADA member state and branches of foreign companies operating in Mali"
          :rep-provenance "https://rccm.ohada.org/staticPage/index?alias=ml"
          :corporate-number-owner-authority "Direction Générale des Impôts (DGI) du Mali"
          ;; The dossier did NOT surface a distinct authoritative
          ;; NIF-specific legal citation (decree/law number) for Mali --
          ;; only that NIF is the standard UEMOA-zone tax ID issued
          ;; alongside RCCM registration, and that DGI is Mali's own tax
          ;; administration by the same institutional convention every
          ;; UEMOA-zone country follows. Stated as institutional
          ;; convention, NOT as a verified decree citation -- do not
          ;; upgrade this to a specific legal-basis number without a
          ;; verified source.
          :corporate-number-legal-basis "NIF (Numéro d'Identification Fiscale) -- issued by DGI alongside RCCM registration, by the same institutional convention every UEMOA-zone country follows; no distinct NIF-specific legal-basis decree verified for this catalog"
          :corporate-number-provenance "https://rccm.ohada.org/staticPage/index?alias=rccm"}
   ;; -- reference jurisdictions, reused verbatim from already-merged
   ;; sibling repos (cloud-itonami-iso3166-ago / -sen), not new claims --
   "USA" {:name "United States" :owner-authority "GSA/SAM.gov" :legal-basis "FAR" :national-spec "SAM.gov" :provenance "https://sam.gov/"
          :required-evidence ["EIN record" "SAM.gov registration record" "State business registration record" "SAM UEI verification record"]}
   "CIV" {:name "Côte d'Ivoire" :owner-authority "ANRMP" :legal-basis "Code des marchés publics" :national-spec "e-procurement" :provenance "https://www.anrmp.ci/"
          :required-evidence ["RCCM record" "e-procurement registration" "RCCM extract" "Authorized-representative record"]}
   "SEN" {:name "Senegal" :owner-authority "ARMP / e-procurement" :legal-basis "Code des marchés publics" :national-spec "e-procurement + RCCM/NINEA" :provenance "https://www.armp.sn/"
          :required-evidence ["RCCM/NINEA record" "e-procurement registration record" "RCCM extract" "Authorized-representative record"]}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note "R0 catalog seed"})))
(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))
(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))
(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))
(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))
