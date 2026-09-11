# cloud-itonami-iso3166-mli

**`:implemented`** for **MLI**. Flagship `rccm-entity-missing`, tax `nif-unverified`.

```
clojure -M:dev:test
```

AGPL-3.0-or-later.

## Market-entry compliance actor

The Actors pattern (containment + independent Governor + append-only
audit ledger, per skill `build-actor`), same architecture as sibling
`cloud-itonami-iso3166-ago` / `-sen`:

- `src/marketentry/facts.cljk` — the spec-basis catalog, grounded ONLY
  in a verified research dossier (ARMDS / DGMP-DSP / RCCM-OHADA / NIF-DGI
  government/regulator sources); a jurisdiction not in `catalog` has no
  spec-basis, full stop.
- `src/marketentry/governor.cljk` — the Market-Entry Compliance
  Governor, 7 HARD checks in priority order (spec-basis,
  evidence-incomplete, `rccm-entity-missing` [flagship], engagement-fee
  mismatch, `nif-unverified`, confidence/actuation gate,
  double-draft/double-submit).
- `src/marketentry/store.cljk` — `MemStore` (dev/test default) and a
  `DatomicStore` built on `langchain-store.core` (`ls/enc`/`ls/dec*`/
  `ls/read-stream`/`ls/append-blob!`), never a hand-rolled codec.
- `src/marketentry/registry.cljk` — pure filing-draft/filing-submit
  record construction + the engagement-fee ground-truth recompute.
- `src/marketentry/marketentryllm.cljk` — the contained, deterministic
  advisor (never commits directly).
- `src/marketentry/operation.cljk` — the langgraph-clj StateGraph
  wiring advise → govern → decide → (commit | request-approval | hold).
- `src/marketentry/phase.cljk` — 0→3 rollout gate; `:filing/draft`/
  `:filing/submit` never auto-commit at any phase.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Mali:

- `src/culture/facts.cljk` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
