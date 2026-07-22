# Operator Guide

Human-gated filing only. `filing/draft` and `filing/submit` always
escalate for human market-entry-operator approval, at every rollout
phase — see `src/marketentry/phase.cljc` and the governor's
confidence/actuation gate in `src/marketentry/governor.cljc`.

Required evidence before a filing may be drafted or submitted (per
`marketentry.facts` for "MLI"):

- RCCM registration record (OHADA)
- NIF record (DGI)
- DGMP-DSP e-procurement registration record
- Authorized-representative record
