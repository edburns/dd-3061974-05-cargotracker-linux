# Copilot CLI Session

> [!NOTE]
> - **Session ID:** `e0f44fef-b70f-488e-830b-2ca36ae1483c`  
> - **Started:** 9/10/2026, 5:34:33 PM  
> - **Duration:** 2m 42s  
> - **Exported:** 9/10/2026, 5:37:15 PM  

---

<sub>1s</sub>

### User

Invoke skill `shepherd-task-20-create-issues-from-plan` with these inputs:

- CAMPAIGN_ID: 34dfbae2-dbd9-4702-ad9a-808b93224026
- LESSON_PROPAGATION: off
- REPO: edburns/dd-3061974-05-cargotracker-linux
- BASE_BRANCH: experiment/shepherd-control
- PARENT_ISSUE: 1
- PLAN_DIRECTORY: 1-arrival-deadline-control-remove-before-merge
- PLAN_FILE_NAME: add-change-arrival-deadline-feature-ignorance-reduction-plan.md
- QUESTIONS_SECTION: ## Phase 3 — Ignorance reduction: questions to answer before writing code
- IMPLEMENTATION_SECTION: ## Phase 4 — Implementation (five serial issues)
- EXPECTED_TASK_COUNT: 5
- BASE_REMOTE: origin
- LOG_DIRECTORY: /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734
- DRAFT_VALIDATOR: /home/edburns/.copilot/plugins/shepherd-task/scripts/validate-stage20-drafts.sh
- ISSUE_BODY_VERIFIER: /home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh

Fixture pagination response contract (mandatory):

- `gh api ... --paginate --slurp` returns a JSON array of page payloads, so a
  one-page response has the shape `[[{...}]]`, not `[{...}]`.
- Before indexing child issue fields such as `.id`, normalize the response to
  one flat issue array exactly once.
- In Bash, use:
  `jq 'if length == 0 then [] elif all(.[]; type == "array") then add else . end'`.
- In PowerShell, capture the `gh` output and `$LASTEXITCODE` first, then pass
  the complete JSON through the same `jq` normalization before
  `ConvertFrom-Json`.
- Use the normalized flat array for the pre-creation baseline, final child
  count/order checks, and failure reconciliation. Do not apply `add` a second
  time to an already-flat array.

---

<sub>4s</sub>

### `skill`

**shepherd-task-20-create-issues-from-plan**

<details>
<summary>Arguments</summary>

```json
{
  "skill": "shepherd-task-20-create-issues-from-plan"
}
```

</details>

```
Skill "shepherd-task-20-create-issues-from-plan" loaded successfully. Follow the instructions in the skill context.
```


---

<sub>7s</sub>

### Copilot

I’m validating the campaign inputs, plan structure, repository state, and existing child baseline before any GitHub mutation.

---

<sub>18s</sub>

### `bash`

**Validate paths, scripts, branch, and remote**

$ set -euo pipefail
LOG='/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734'
test -d "$LOG" && test -w "$LOG"
mkdir -p "$LOG/issue-bodies"
test -x '/home/edburns/.copilot/plugins/shepherd-task/scripts/validate-stage20-drafts.sh'
test -x '/home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh'
printf 'filesystem-validation=ok\n'
git status --short
git remote -v
git branch --list 'experiment/shepherd-control'
git ls-remote --exit-code --heads origin 'experiment/shepherd-control' >/dev/null
printf 'branch-validation=ok\n'

<details>
<summary>11 lines</summary>

```
filesystem-validation=ok
?? 1-arrival-deadline-control-remove-before-merge/.stage20-after.111688
?? 1-arrival-deadline-control-remove-before-merge/.stage20-before.111688
?? 1-arrival-deadline-control-remove-before-merge/prompts/
origin	git@github.com:edburns/dd-3061974-05-cargotracker-linux.git (fetch)
origin	git@github.com:edburns/dd-3061974-05-cargotracker-linux.git (push)
upstream	git@github.com:azure-javaee/cargotracker.git (fetch)
upstream	git@github.com:azure-javaee/cargotracker.git (push)
* experiment/shepherd-control
branch-validation=ok
<shellId: 3 completed with exit code 0>
```

</details>


---

<sub>18s</sub>

### `bash`

**Validate repository, parent, and issue type**

$ set -euo pipefail
REPO='edburns/dd-3061974-05-cargotracker-linux'
repo_json="$(gh api "repos/$REPO")"
printf '%s' "$repo_json" | jq '{default_branch,owner:{login:.owner.login,type:.owner.type}}'
gh api "repos/$REPO/issues/1" --jq '{number,state,title,repository_url}'
owner_type="$(printf '%s' "$repo_json" | jq -r '.owner.type')"
owner="$(printf '%s' "$repo_json" | jq -r '.owner.login')"
if [ "$owner_type" = Organization ]; then gh api "orgs/$owner/issue-types" | jq '[.[] | select(.name == "Task" and .is_enabled == true)]'; elif [ "$owner_type" = User ]; then printf '[]\n'; else printf 'unrecognized owner type: %s\n' "$owner_type" >&2; exit 1; fi

<details>
<summary>10 lines</summary>

```
{
  "default_branch": "20260902-2104Z-commit-e7b651f-liberty",
  "owner": {
    "login": "edburns",
    "type": "User"
  }
}
{"number":1,"repository_url":"https://api.github.com/repos/edburns/dd-3061974-05-cargotracker-linux","state":"open","title":"[Campaign][off] Cargo Tracker arrival-deadline control"}
[]
<shellId: 0 completed with exit code 0>
```

</details>


---

<sub>18s</sub>

### `bash`

**Capture normalized child baseline**

$ set -euo pipefail
REPO='edburns/dd-3061974-05-cargotracker-linux'
LOG='/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734'
raw="$(gh api "repos/$REPO/issues/1/sub_issues" --paginate --slurp)"
printf '%s' "$raw" | jq 'if length == 0 then [] elif all(.[]; type == "array") then add else . end' > "$LOG/pre-creation-children.json"
jq '[.[] | {id,number,title}]' "$LOG/pre-creation-children.json"

```
[]
<shellId: 1 completed with exit code 0>
```


---

<sub>18s</sub>

### `bash`

**Read campaign plan and resource inventory**

$ set -euo pipefail
git show 'experiment/shepherd-control:1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md'
printf '\n---CAMPAIGN---\n'
git show 'experiment/shepherd-control:1-arrival-deadline-control-remove-before-merge/shepherd-campaign.json'
printf '\n---LESSONS-PRESENT---\n'
git cat-file -e 'experiment/shepherd-control:1-arrival-deadline-control-remove-before-merge/campaign-lessons.md' && printf 'yes\n'
printf '\n---RESOURCE-FILES---\n'
git ls-tree -r --name-only 'experiment/shepherd-control' -- '1-arrival-deadline-control-remove-before-merge'

<details>
<summary>11 lines</summary>

```
Output too large to read at once (34.5 KB). Saved to: /tmp/1789061690957-copilot-tool-output-111823-5f73fbec-71ff-4874-9506-81579f0626d5.txt
Consider using tools like rg (for searching), head/tail (for viewing start/end), view with view_range (for specific sections), or jq (for JSON) to examine portions of the output.

Preview (first 500 chars):
# Implementation plan: Change Arrival Deadline Date (`eclipse-ee4j/cargotracker#64`)

Human DRI: Ed Burns
Starting commit: `9b9f311b2a3a2854bdac947593950d9edb6bca7d` (`Make the system ready for implementation`)
Working directory: repository root of the current campaign worktree
Runtime baseline: Java 17, Java EE 7 (`javax.*`), Open Liberty 26.0.0.8, PrimeFaces 8.0
Baseline run instructions: `README.md`
Baseline preparation: fixed source branch and immutable SHA validated by the campaign fixture

<shellId: 2 completed with exit code 0>
```

</details>


---

<sub>18s</sub>

### `view`

**view the file at /home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/01-1682-java-tool-ergonomics.md.**

/home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/01-1682-java-tool-ergonomics.md (lines 1--1)

<details>
<summary>15 lines</summary>

```
# Example 01: 1682-java-tool-ergonomics-prompts-remove-before-merge

Read `copilot-sdk/1682-java-tool-ergonomics-prompts-remove-before-merge/dd-3018003-ignorance-reduction-for-implementation-plan.md` (the plan) to get the context.

✅✅Pay very close attention to each **Resolution:** to the questions in `Phase 3 — Ignorance reduction: questions to answer before writing code`.

In this session, we are creating issues of type **Task** as children of Epic https://github.com/github/copilot-sdk/issues/1682 . These issues correspond to the sub-sections in `## Phase 4 — Implementation (the build order)`.

I want you to use `gh` to create issues as sub-issues of 1682.

- I will dispatch these issues by assigning them to an agent. Leave them unassigned for now.

- The description you put in the issues you create is the specification for the work the agent assigned to the issue must do. Therefore, you need to be very specific and targeted when writing the description.

- Set the type to **Task**.

- Each issue will be worked on and resolved in serial. You can assume:
  - All issues will operate in this topic branch of `upstream`: `edburns/1682-java-tool-ergonomics`.

  - The assignee agent has access to the topic branch and, most importantly, to the prompts in the directory `1682-java-tool-ergonomics-prompts-remove-before-merge`, which includes the plan. Therefore, you can instruct the agent to access the plan by reference, you don't need to reference it by value directly in the issue.

- The sub-sections of `## Phase 4 — Implementation (the build order)` have essential details for each corresponding **Task**.

- Instruct the agent to read the entire plan before working.

- Instruct the agent to carefully re-read the relevant sections of the of the plan. ❌❌You can't just say, "read the relevant sections of the plan". ✅✅In the description of the issue, list exactly what sections of the plan apply to this particular issue.
- Though the sub-sections of `## Phase 4 — Implementation (the build order)` have essential details for each corresponding **Task**, I want you to carefully consider if there additional gating tests you can add so that as the agent proceeds from one task to the next, the likelihood of rework is reduced.
```

</details>


---

<sub>18s</sub>

### `view`

**view the file at /home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/02-1810-java-tool-as-lambda.md.**

/home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/02-1810-java-tool-as-lambda.md (lines 1--1)

<details>
<summary>12 lines</summary>

```
# Example 02: 1810-java-tool-ergonomics-tool-as-lambda-remove-before-merge

Carefully read the plan `copilot-sdk/1810-java-tool-ergonomics-tool-as-lambda-remove-before-merge/1810-ignorance-reduction-for-implementation-plan.md`.

We have completed Phases 1 - 3. We are now on Phase 4.

For each sub item in Phase 4, file a child issue of Feature #1810.

Use `gh` to look at Epic #1682 and its child issues for the standard of verbosity and concreteness for issue descriptions.

The issue should be assignable to a coding agent and there must be sufficient detail in the description for the agent to proceed to completion.

Pull out the specific relevant content from Phase 3 as it applies to this current issue in Phase 4.

Tell the agent to take `edburns/1810-java-tool-ergonomics-tool-as-lambda` as the base branch. When I assign the issue to the coding agent, I will set this as the base branch.

Tell the agent to read `1810-java-tool-ergonomics-tool-as-lambda-remove-before-merge/1810-ignorance-reduction-for-implementation-plan.md`, which is present on the branch, for context.

Tell the agent which sub-item within Phase 4 they are solving.

Tell the agent that each issue will be assigned, resolved and merged to the base branch in the order in which they appear in the plan.

No work is to start until I assign each issue to the coding agent, in turn.
```

</details>


---

<sub>18s</sub>

### `view`

**view the file at /home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/03-dd-3017826-java-real-estate-demo.md.**

/home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/03-dd-3017826-java-real-estate-demo.md (lines 1--1)

<details>
<summary>14 lines</summary>

```
# Example 03: dd-3017826-java-real-estate-demo-remove-before-merge

Read `dd-3017826-java-real-estate-demo-remove-before-merge\dd-3017826-java-real-estate-demo-ignorance-reduction-plan.md` (the plan) to get the context.

✅✅Pay very close attention to each **Resolution:** to the questions in `Phase 2 — Ignorance reduction: questions to answer before writing code`.

In this session, we are creating sub-issues as children of https://github.com/edburns/Build26-BRK206-your-agent-anywhere-multiclient-multidevice-with-github-copilot-sdk/issues/2 . These issues correspond to the sub-sections in `## Phase 3 — Implementation (build order)`.

I want you to use `gh` to create issues as sub-issues of #2.

- I will dispatch these issues by assigning them to an agent. Leave them unassigned for now.

- The description you put in the issues you create is the specification for the work the agent assigned to the issue must do. Therefore, you need to be very specific and targeted when writing the description.

- Each issue will be worked on and resolved in serial. You can assume:
  - All issues will operate in this topic branch of `origin`: `edburns/2-build-out-demo`.

  - The assignee agent has access to the topic branch and, most importantly, to the prompts in the directory `dd-3017826-java-real-estate-demo-remove-before-merge`, which includes the plan. Therefore, you can instruct the agent to access the plan by reference, you don't need to reference it by value directly in the issue.

- The sub-sections of `## Phase 3 — Implementation (build order)` have essential details for each corresponding sub-issue.

- Instruct the agent to read the entire plan before working.

- Instruct the agent to carefully re-read the relevant sections of the of the plan. ❌❌You can't just say, "read the relevant sections of the plan". ✅✅In the description of the issue, list exactly what sections of the plan apply to this particular issue.
- Though the sub-sections of `## Phase 3 — Implementation (build order)` have essential details for each corresponding sub-issue, I want you to carefully consider if there additional gating tests you can add so that as the agent proceeds from one task to the next, the likelihood of rework is reduced.
```

</details>


---

<sub>18s</sub>

### `view`

**view the file at /home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/04-28-python-agent-demo.md.**

/home/edburns/.copilot/skills/shepherd-task-20-create-issues-from-plan/examples/04-28-python-agent-demo.md (lines 1--1)

<details>
<summary>16 lines</summary>

```
# Example 04: 28-python-agent-demo-remove-before-merge

Read `28-python-agent-demo-remove-before-merge/28-python-agent-demo-ignorance-reduction-plan.md` (the plan) to get the context.

✅✅Pay very close attention to each **Resolution:** to the questions in `Phase 2 — Ignorance reduction: questions to answer before writing code`. These pertain to the implementation tasks in Phase 3. ✅✅✅ Make sure to put into the description explicit instructions on how to access the relevant spike research.

In this session, we are creating sub-issues as children of https://github.com/edburns/Build26-BRK206-your-agent-anywhere-multiclient-multidevice-with-github-copilot-sdk/issues/28 . These issues correspond to the sub-sections in `## Phase 3 — Implementation (build order)`.

I want you to use `gh` to create issues as sub-issues of #28

- I will dispatch these issues by assigning them to an agent. Leave them unassigned for now.

- The description you put in the issues you create is the specification for the work the agent assigned to the issue must do. Therefore, you need to be very specific and targeted when writing the description.

- Again, ✅✅For each **Resolution:** to the questions in `Phase 2 — Ignorance reduction: questions to answer before writing code`, include the relevant Resolution values in the issue description. These pertain to the implementation tasks in Phase 3. ✅✅✅ Make sure to put into the description explicit instructions on how to access the relevant spike research for that task.

- For tasks dealing with the UI, make sure to refer to the relevant screengrabs, but only ones within `28-python-agent-demo-remove-before-merge/`.

- Each issue will be worked on and resolved in serial. You can assume:
  - All issues will operate in this topic branch of `origin`: `edburns/28-python-agent-demo`.

  - The assignee agent has access to the topic branch and, most importantly, to the prompts in the directory `28-python-agent-demo-remove-before-merge/`, which includes the plan. Therefore, you can instruct the agent to access the plan by reference, you don't need to reference it by value directly in the issue.

- The sub-sections of `## Phase 3 — Implementation (build order)` have essential details for each corresponding sub-issue.

- Instruct the agent to read the entire plan before working.

- Instruct the agent to carefully re-read the relevant sections of the of the plan. ❌❌You can't just say, "read the relevant sections of the plan". ✅✅In the description of the issue, list exactly what sections of the plan apply to this particular issue.
- Though the sub-sections of `## Phase 3 — Implementation (build order)` have essential details for each corresponding sub-issue, I want you to carefully consider if there additional gating tests you can add so that as the agent proceeds from one task to the next, the likelihood of rework is reduced.
```

</details>


---

<sub>24s</sub>

### Copilot

The invocation is valid: the parent is open, the base branch is non-default and matches `origin`, no children exist yet, and this user-owned repository will use untyped issues. I’m now extracting the five task specifications and all resolved constraints into persisted drafts.

---

<sub>28s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md (lines 1-220)

<details>
<summary>171 lines</summary>

````
# Implementation plan: Change Arrival Deadline Date (`eclipse-ee4j/cargotracker#64`)

Human DRI: Ed Burns
Starting commit: `9b9f311b2a3a2854bdac947593950d9edb6bca7d` (`Make the system ready for implementation`)
Working directory: repository root of the current campaign worktree
Runtime baseline: Java 17, Java EE 7 (`javax.*`), Open Liberty 26.0.0.8, PrimeFaces 8.0
Baseline run instructions: `README.md`
Baseline preparation: fixed source branch and immutable SHA validated by the campaign fixture
Historical issue: `eclipse-ee4j/cargotracker#64`

Related directories and files:

- `src/main/java/org/eclipse/cargotracker/application/`
- `src/main/java/org/eclipse/cargotracker/interfaces/booking/facade/`
- `src/main/java/org/eclipse/cargotracker/interfaces/booking/web/`
- `src/main/webapp/admin/dialogs/`
- `src/main/webapp/admin/tables/listNotRouted.xhtml`
- `src/test/java/org/eclipse/cargotracker/application/BookingServiceTest.java`

---

## Goal

Add an Administration dashboard operation that lets a shipping administrator
change the arrival deadline of a cargo listed in the **Not Routed Cargo** table.
The operation must preserve Cargo Tracker's layered architecture:

1. The application service owns the domain mutation.
2. The booking facade shields the web layer from domain types.
3. A JSF backing bean loads and submits the editable date.
4. A PrimeFaces dynamic dialog presents the editor.
5. The existing Not Routed Cargo table opens the dialog and refreshes after a
   successful update.

### User-visible acceptance behavior

Using the stable sample cargo `DEF789`:

1. Start the application with Java 17:

   ```bash
   ./mvnw clean package -Popenliberty liberty:run
   ```

2. Open `http://localhost:8080/cargo-tracker/`.
3. Select **Administration**.
4. Find `DEF789` in the **Not Routed Cargo** table.
5. The Deadline cell displays its date together with an edit icon.
6. Hovering over the deadline displays:
   `Click to change cargo arrival deadline date.`
7. Selecting the deadline opens a modal dialog titled **Change Deadline**.
8. The dialog displays the cargo's origin and destination as read-only
   context.
9. The date editor is initialized to the cargo's current arrival deadline.
10. Selecting a different date and pressing **Update** closes the dialog and
    refreshes the Administration view.
11. The new date is shown in the Not Routed Cargo table.
12. Reloading the page continues to show the new date for the lifetime of the
    running in-memory sample application.
13. Pressing **Cancel** closes the dialog without changing the deadline.

### Domain acceptance behavior

Changing the deadline must:

- locate the cargo by `TrackingId`;
- preserve its existing origin;
- preserve its existing destination;
- replace only the arrival deadline in its `RouteSpecification`;
- apply the specification through `Cargo.specifyNewRoute(...)`;
- preserve the currently assigned itinerary rather than silently discarding
  it;
- allow the domain model to recalculate routing status and delivery-derived
  values against the new route specification;
- persist the changed cargo through `CargoRepository.store(...)`.

### Hard scope constraints

- Begin from commit `9b9f311b2a3a2854bdac947593950d9edb6bca7d`.
- Preserve Java EE 7 and the `javax.*` namespace.
- Preserve the Java 7 source/target level used by this historical codebase.
- Run the application on JDK 17 using the existing Open Liberty profile.
- Do not migrate the application to Jakarta EE 8+, Jakarta EE 9+, Spring, or a
  different UI framework.
- Do not replace the in-memory Derby configuration or the Open Liberty runtime.
- Do not redesign unrelated cargo booking, routing, destination editing,
  messaging, batch, REST, or persistence behavior.
- Do not copy commits or files from feature-bearing branches. This plan is the
  implementation specification.
- Implement the five build issues below in order. Each issue must be complete
  and gated before the next issue begins.

---

## Completed phases

### Phase 1 ✅ — Establish a runnable feature-absent baseline

- Commit `9b9f311b2a3a2854bdac947593950d9edb6bca7d` is based on the historical
  feature-absent commit and contains only the compatibility work needed to run
  the sample on JDK 17 and Open Liberty.
- `./mvnw clean package -Popenliberty liberty:run` starts the application.
- The home page and Administration flows return HTTP 200.
- JSF view metadata is placed at `UIViewRoot` scope for MyFaces compatibility.
- The internal routing REST client works without a Jersey/MOXy classloading
  conflict.
- The scheduled batch job has the local authorization it needs.

### Phase 2 ✅ — Verify the before and after user experience

- Before implementation, `DEF789` appears in the Not Routed Cargo table with a
  plain-text deadline and no edit operation.
- The neighboring Destination column demonstrates the existing PrimeFaces
  dynamic-dialog interaction pattern.
- The desired after behavior has been manually exercised: open the deadline
  editor, choose a new date, update, refresh the table, and observe the
  persisted value.
- The historical architectural boundaries and affected files have been
  identified.

---

## Phase 3 — Ignorance reduction: questions to answer before writing code

Resolve these questions before production implementation begins. The
recommendations intentionally define the desired design closely enough that an
implementing agent should not need to invent a different architecture.

### 3.1 — Which cargos expose the edit operation?

**Question:** Should deadline editing be exposed for all cargos or only for
cargos displayed in the Not Routed Cargo table?

The requested feature originates in the Administration dashboard's Not Routed
Cargo table. Other tables represent routed, misrouted, claimed, or otherwise
progressed cargo. Adding the affordance to every table would expand the feature
and require additional business rules about changing deadlines after handling
has begun.

| Option | UI scope | Trade-off |
|--------|----------|-----------|
| A | Not Routed Cargo table only | Matches the requested feature and the established destination-edit affordance. |
| B | Every Administration cargo table | Broader capability, but introduces lifecycle and authorization questions outside the request. |
| C | Cargo details page only | Avoids table complexity but does not meet the requested dashboard interaction. |

The application-service operation itself does not need to encode a UI-table
restriction. It should accept a tracking ID and apply the domain mutation to
the located cargo. The presentation layer determines where the operation is
offered.

**Recommendation:** Option A. Add the edit affordance only to
`src/main/webapp/admin/tables/listNotRouted.xhtml`. Keep the application
operation generally usable for a valid cargo.

**Resolution:**

Select Option A. Expose the edit affordance only in
`src/main/webapp/admin/tables/listNotRouted.xhtml`. The application and facade
operations remain generally callable for any cargo that can be found by
tracking ID; they do not encode knowledge of dashboard table membership.

### 3.2 — What is the exact domain mutation?

**Question:** Should the feature mutate the existing `RouteSpecification`, add
a setter to `Cargo`, or replace the specification using the existing domain
operation?

`RouteSpecification` is a value object describing origin, destination, and
arrival deadline. The existing `changeDestination(...)` implementation already
establishes the correct pattern: create a replacement specification, call
`Cargo.specifyNewRoute(...)`, and store the aggregate.

Proposed application-service shape:

```java
void changeDeadline(TrackingId trackingId, Date deadline);
```

Proposed implementation:

```java
Cargo cargo = cargoRepository.find(trackingId);
RouteSpecification specification = new RouteSpecification(
        cargo.getOrigin(),
        cargo.getRouteSpecification().getDestination(),
        deadline);

cargo.specifyNewRoute(specification);
cargoRepository.store(cargo);
```

Calling `specifyNewRoute(...)` is significant. It lets the aggregate recalculate
delivery and routing status relative to the new specification. Direct field
mutation or a persistence-only update would bypass that behavior.

**Recommendation:** Replace the `RouteSpecification` through
`Cargo.specifyNewRoute(...)`. Preserve origin, destination, and itinerary.
Persist using the existing repository. Do not add a deadline setter to the
domain model.

**Resolution:**

Use the same aggregate-update pattern as `changeDestination(...)`. Add
`BookingService.changeDeadline(TrackingId, Date)` and implement it by loading
the cargo, constructing a new `RouteSpecification` from the existing origin,
existing destination, and supplied deadline, calling
`cargo.specifyNewRoute(...)`, and storing the cargo through
`cargoRepository.store(...)`. Do not add mutable deadline setters to the domain
objects.

### 3.3 — What should happen to an existing itinerary and delivery state?

**Question:** When a routed cargo's deadline changes, should its itinerary be
cleared, retained, or recomputed?

Although the UI initially exposes the feature only for unrouted cargo, the
application operation should have deterministic domain behavior if invoked for
a routed cargo. The existing `changeDestination(...)` behavior preserves the
assigned itinerary and lets `Cargo.specifyNewRoute(...)` recalculate whether
that itinerary still satisfies the new specification.
````

</details>


---

<sub>28s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md (lines 221-440)

<details>
<summary>162 lines</summary>

````

The core application test should deliberately invoke the operation after:

1. booking a cargo;
2. requesting route candidates;
3. assigning an itinerary;
4. changing its destination;
5. changing its deadline.

This sequence verifies that the feature uses the aggregate correctly rather
than assuming the cargo always has an empty itinerary.

**Recommendation:** Preserve the itinerary. Let the domain model recompute
routing and delivery-derived state. Assert all unaffected fields explicitly in
`BookingServiceTest`.

**Resolution:**

Retain the existing itinerary. Do not clear, replace, or reroute it as part of
the deadline change. `Cargo.specifyNewRoute(...)` recalculates the delivery
snapshot and routing status against the replacement specification. In the
established sequential application test, the assigned itinerary remains
unchanged and the cargo remains `MISROUTED` after the deadline changes.

### 3.4 — What type crosses the facade boundary?

**Question:** Should the booking facade accept a `Date`, a formatted string, or
a newly introduced request DTO?

The existing facade already uses `java.util.Date` for
`bookNewCargo(...)`. Introducing another representation for this one operation
would create unnecessary conversion code and depart from the historical
application style.

Proposed facade shape:

```java
void changeDeadline(String trackingId, Date arrivalDeadline);
```

The implementation converts only the identifier:

```java
bookingService.changeDeadline(
        new TrackingId(trackingId),
        arrivalDeadline);
```

**Recommendation:** Use `String` for the tracking ID and `java.util.Date` for
the deadline. Do not expose `TrackingId`, `Cargo`, or `RouteSpecification` to
the JSF layer and do not introduce a new DTO solely for this command.

**Resolution:**

Add `void changeDeadline(String trackingId, Date arrivalDeadline)` to
`BookingServiceFacade`. `DefaultBookingServiceFacade` converts the string to
`new TrackingId(trackingId)` and passes the same `Date` to
`BookingService.changeDeadline(...)`. No new command DTO or formatted-string
service parameter is introduced.

### 3.5 — How is the DTO's formatted deadline converted for editing?

**Question:** `CargoRoute` exposes its deadline as formatted strings, while
`p:datePicker` binds naturally to `java.util.Date`. How should the backing bean
initialize the editor?

At the starting commit:

- `CargoRoute.getArrivalDeadline()` returns
  `MM/dd/yyyy hh:mm a z`.
- `CargoRoute.getArrivalDeadlineDate()` returns only the date component.
- The table displays `getArrivalDeadlineDate()`.

Options:

| Option | Approach | Trade-off |
|--------|----------|-----------|
| A | Parse `cargo.getArrivalDeadlineDate()` with `MM/dd/yyyy` | Small, localized change; preserves the existing DTO contract. |
| B | Add a `Date` property to `CargoRoute` | Cleaner typing, but broadens a DTO used throughout the application. |
| C | Reload the domain object in the backing bean | Violates the facade boundary. |

The formatter/parser must be created per operation or per view bean; do not add
a shared mutable `SimpleDateFormat`.

**Recommendation:** Option A. Load `CargoRoute` through
`BookingServiceFacade.loadCargoForRouting(trackingId)` and parse
`cargo.getArrivalDeadlineDate()` using `new SimpleDateFormat("MM/dd/yyyy")`.
Surface an explicit failure if the existing DTO value cannot be parsed; do not
silently submit a null date.

**Resolution:**

Use Option A and keep date conversion inside the view-scoped editor bean. The
existing implementation loads the `CargoRoute`, creates
`new SimpleDateFormat("MM/dd/yyyy")`, and parses the leading date portion of
`cargo.getArrivalDeadline()`. Because that value begins with `MM/dd/yyyy`,
`SimpleDateFormat.parse(...)` obtains the same date that
`getArrivalDeadlineDate()` displays. A per-load formatter is used, so no shared
mutable formatter is added.

### 3.6 — Which JSF bean scopes and interaction pattern should be used?

**Question:** Should deadline editing introduce a new navigation page, use an
inline editor, or mirror the existing Change Destination dynamic-dialog
pattern?

The baseline already contains:

- `ChangeDestination`, a CDI `@Named` and JSF `@ViewScoped` editor bean;
- `ChangeDestinationDialog`, a session-scoped JSF managed bean that opens and
  closes a PrimeFaces dynamic dialog;
- `changeDestination.xhtml`, a dialog view;
- a `dialogReturn` Ajax listener that refreshes `tableNotRouted`.

Using the same pattern minimizes changes and provides a consistent user
experience.

Proposed bean names:

```text
changeArrivalDeadlineDate
changeArrivalDeadlineDateDialog
```

**Recommendation:** Add a serializable CDI `@Named @ViewScoped`
`ChangeArrivalDeadlineDate` editor and a serializable
`@ManagedBean(name = "changeArrivalDeadlineDateDialog") @SessionScoped`
launcher. Mirror the existing destination-dialog lifecycle rather than
introducing a new navigation or inline-edit framework.

**Resolution:**

Mirror the existing Change Destination interaction. Implement
`ChangeArrivalDeadlineDate` as a serializable CDI `@Named @ViewScoped` bean and
`ChangeArrivalDeadlineDateDialog` as a serializable
`@ManagedBean(name = "changeArrivalDeadlineDateDialog") @SessionScoped` bean.
Use a PrimeFaces dynamic dialog rather than navigation to a full page or inline
cell editing.

### 3.7 — What is the dynamic-dialog contract?

**Question:** What path, request parameters, dimensions, and close result should
the PrimeFaces dialog use?

The launcher needs one parameter, `trackingId`, supplied as a
`Map<String, List<String>>`. The dialog metadata binds the parameter and invokes
the editor bean's `load()` action.

Proposed launcher contract:

```java
PrimeFaces.current().dialog().openDynamic(
        "/admin/dialogs/changeArrivalDeadlineDate.xhtml",
        options,
        params);
```

Required options:

| Option | Value |
|--------|-------|
| `modal` | `true` |
| `draggable` | `true` |
| `resizable` | `false` |
| `contentWidth` | `410` |
| `contentHeight` | `280` |

Required completion behavior:

- successful update: `closeDynamic("DONE")`;
- cancel: `closeDynamic("")`;
- caller listens for `dialogReturn` and updates `tableNotRouted`.

Because Open Liberty uses MyFaces, `<f:metadata>` must be a direct child of the
view root, before `<h:head>` and `<h:body>`. It must not be nested inside
`<h:body>`.

**Recommendation:** Use the contract above and preserve the metadata placement
required by the prepared baseline.

**Resolution:**

Open `/admin/dialogs/changeArrivalDeadlineDate.xhtml` with a single
`trackingId` request parameter and these options: modal and draggable are
`true`, resizable is `false`, content width is `410`, and content height is
`280`. Successful submission closes with `"DONE"`; cancellation closes with
the empty string. The caller handles `dialogReturn` and updates
`tableNotRouted`. Place the dialog's `<f:metadata>` directly under the root
`<html>` element, before `<h:head>` and `<h:body>`, so the known MyFaces
`UIViewRoot` requirement is satisfied.

### 3.8 — What date validation is required?

**Question:** Must the new deadline be non-null, in the future, after the
current date, or after itinerary completion?

The requested feature is an administrative correction to an existing arrival
deadline. No new domain policy about future dates is part of the request.
Inventing such a rule could reject dates accepted by existing cargo booking or
`RouteSpecification` behavior.

The UI must nevertheless prevent a null submission because the operation
requires a concrete replacement deadline.

**Recommendation:** Require a date value in the JSF form and display a normal
Faces validation message when it is absent. Do not add a new minimum-date,
future-date, or itinerary-date business rule. Continue to rely on the existing
domain model for its established invariants.

**Resolution:**

Require a non-null date selection, but add no new chronological business rule.
In particular, do not require the replacement deadline to be after today,
after the old deadline, or after every itinerary leg. Pass the selected
`java.util.Date` to the existing domain construction path and let the current
`RouteSpecification` invariants apply.

### 3.9 — How will the feature be tested on the prepared historical baseline?

**Question:** Which automated and runtime tests are mandatory, given that the
````

</details>


---

<sub>28s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md (lines 441-700)

<details>
<summary>189 lines</summary>

````
historical JUnit/Arquillian suite is configured for a remote Payara 4
container, while the prepared production baseline runs on JDK 17/Open Liberty?

The starting POM deliberately leaves `skipTests=true`. The Open Liberty profile
builds and compiles all test sources but does not provide a Liberty Arquillian
adapter. Modernizing the entire integration-test runtime is outside this
feature's scope.

The feature still needs layered evidence:

1. Extend `BookingServiceTest` with the domain/application assertions that
   specify the deadline mutation.
2. Ensure all test sources compile as part of
   `./mvnw clean package -Popenliberty`.
3. Add focused JUnit tests for facade and backing-bean delegation where they
   can run without a container, using hand-written fakes rather than adding a
   mocking framework.
4. Perform mandatory end-to-end verification against the running Open Liberty
   application.
5. Preserve the existing Payara Arquillian test path; do not delete, disable,
   or rewrite it to manufacture a passing result.

**Spike needed:** Before Issue 1 implementation, run the starting commit's
standard Open Liberty package command and record whether tests are compiled but
skipped. Confirm the new `BookingServiceTest` method can be added without
expanding the runtime modernization scope.

**Recommendation:** Treat the JDK 17/Open Liberty build plus HTTP/UI acceptance
as the mandatory executable gate. Keep the historical Arquillian test as a
precise application-layer specification and run it only when its documented
Payara environment is available.

**Resolution:**

Extend the existing sequential Arquillian `BookingServiceTest` with
`testChangeDeadline()` after `testChangeDestination()`. The test changes the
deadline by one month, reloads the cargo through JPA, and asserts the complete
set of preserved and recalculated domain state described above. The prepared
Open Liberty build compiles this test but retains the historical default
`skipTests=true`; executing that Arquillian suite still requires its documented
remote Payara environment. Therefore the mandatory executable gates are the
JDK 17 Open Liberty package/start command, direct HTTP checks, and the complete
`DEF789` browser acceptance flow. No Arquillian-runtime modernization or new
mocking dependency is part of this feature.

---

## Phase 4 — Implementation (five serial issues)

Implement these issues in order. Each issue should be a separate commit. Do not
start an issue until the previous issue's gating criteria are satisfied.

### 4.1 — Issue 1: Add the application-layer deadline change operation

**What to build**

Add the core use case to the application layer. This issue must contain no JSF
or PrimeFaces changes.

**Files to modify**

- `src/main/java/org/eclipse/cargotracker/application/BookingService.java`
- `src/main/java/org/eclipse/cargotracker/application/internal/DefaultBookingService.java`
- `src/test/java/org/eclipse/cargotracker/application/BookingServiceTest.java`

**Required API**

```java
void changeDeadline(TrackingId trackingId, Date deadline);
```

**Required implementation behavior**

1. Load the cargo using `cargoRepository.find(trackingId)`.
2. Obtain the current destination from
   `cargo.getRouteSpecification().getDestination()`.
3. Construct a replacement `RouteSpecification` from:
   - `cargo.getOrigin()`;
   - the current destination;
   - the new deadline.
4. Apply it using `cargo.specifyNewRoute(routeSpecification)`.
5. Persist using `cargoRepository.store(cargo)`.
6. Log the tracking ID and new deadline at `Level.INFO`, following the style of
   `changeDestination(...)`.

Do not:

- add a setter to `Cargo` or `RouteSpecification`;
- modify the origin or destination;
- clear or replace the itinerary directly;
- update persistence entities behind the aggregate's back.

**Tests to write first**

Append a sequential `testChangeDeadline()` case to `BookingServiceTest` after
`testChangeDestination()`. Build a new deadline one month after the test's
original `deadline`, invoke the service, reload the cargo with
`Cargo.findByTrackingId`, and assert:

- origin remains Chicago;
- destination remains Helsinki;
- stored deadline is the same calendar day as the requested new deadline;
- assigned itinerary remains unchanged;
- transport status remains `NOT_RECEIVED`;
- last known location remains `Location.UNKNOWN`;
- current voyage remains `Voyage.NONE`;
- cargo is not marked misdirected;
- estimated time of arrival is `Delivery.ETA_UNKOWN`;
- next expected activity is `Delivery.NO_ACTIVITY`;
- cargo is not unloaded at destination;
- routing status reflects the domain model's recalculation and remains
  `MISROUTED` for the established test sequence.

**Gating criteria**

- The test source compiles.
- `./mvnw clean package -Popenliberty` succeeds on JDK 17.
- No web, facade, REST, Liberty, or persistence configuration files change in
  this issue.

### 4.2 — Issue 2: Expose deadline changes through the booking facade

**What to build**

Expose the use case to presentation clients without leaking domain identifier
types into the web layer.

**Files to modify**

- `src/main/java/org/eclipse/cargotracker/interfaces/booking/facade/BookingServiceFacade.java`
- `src/main/java/org/eclipse/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacade.java`

**Optional focused test file**

- `src/test/java/org/eclipse/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacadeTest.java`

**Required API**

```java
void changeDeadline(String trackingId, Date arrivalDeadline);
```

**Required implementation**

```java
bookingService.changeDeadline(
        new TrackingId(trackingId),
        arrivalDeadline);
```

The facade must not:

- load and mutate `Cargo` itself;
- call `CargoRepository.store(...)`;
- parse a formatted date;
- introduce JSF or PrimeFaces types.

**Tests**

Where a container-free test is added, use a hand-written `BookingService` fake
or spy and prove that:

- the same `Date` object/value reaches the application service;
- the tracking-ID string is converted to an equivalent `TrackingId`;
- the facade delegates exactly once;
- no repository work is duplicated in the facade.

Do not add Mockito or another dependency solely for this test.

**Gating criteria**

- Existing facade consumers still compile.
- `./mvnw clean package -Popenliberty` succeeds on JDK 17.
- The application-layer test added in Issue 1 remains unchanged and compiling.

### 4.3 — Issue 3: Implement the deadline editor backing model

**What to build**

Add the view-scoped backing bean that loads a cargo's current deadline and
submits a replacement deadline through the booking facade. Do not add the
dialog launcher or XHTML in this issue.

**File to create**

- `src/main/java/org/eclipse/cargotracker/interfaces/booking/web/ChangeArrivalDeadlineDate.java`

**Required bean shape**

```java
@Named
@ViewScoped
public class ChangeArrivalDeadlineDate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String trackingId;
    private CargoRoute cargo;
    private Date arrivalDeadlineDate;

    @Inject
    private BookingServiceFacade bookingServiceFacade;
}
```

Required properties and methods:

- `getTrackingId()` / `setTrackingId(String)`
- `getCargo()`
- `getArrivalDeadlineDate()` / `setArrivalDeadlineDate(Date)`
- `load()`
- `changeArrivalDeadline()`

**Load behavior**

1. Call `bookingServiceFacade.loadCargoForRouting(trackingId)`.
2. Store the returned `CargoRoute`.
3. Parse `cargo.getArrivalDeadlineDate()` using `MM/dd/yyyy`.
4. Store the resulting `Date` in `arrivalDeadlineDate`.
5. Do not query a repository or domain object directly.
6. Do not ignore a parsing failure or merely print its stack trace. Surface a
   clear application/view error consistent with existing JSF behavior.

**Submit behavior**

1. Refuse a null date through JSF validation or explicit bean validation.
2. Call
   `bookingServiceFacade.changeDeadline(trackingId, arrivalDeadlineDate)`.
3. Close the dynamic dialog with:

   ```java
   PrimeFaces.current().dialog().closeDynamic("DONE");
   ```

4. Do not close the dialog if the facade call fails.

**Tests to write**

Add a container-free JUnit test if practical, using a hand-written fake facade,
that proves:

- `load()` requests the correct tracking ID;
- `load()` converts an `MM/dd/yyyy` DTO date into the editable `Date`;
- `changeArrivalDeadline()` delegates the selected date and tracking ID;
- a malformed DTO deadline is surfaced rather than converted to null;
- a null selected date is rejected.

Do not add a mocking framework solely for these tests.

**Gating criteria**

- The bean is serializable and uses the established CDI/JSF annotations.
- The bean references only facade DTOs, not domain model classes.
- `./mvnw clean package -Popenliberty` succeeds on JDK 17.

### 4.4 — Issue 4: Implement the PrimeFaces deadline dialog

**What to build**

Add the session-scoped dialog launcher and the dynamic dialog view. The dialog
must work when addressed directly with a `trackingId` query parameter, but it
````

</details>


---

<sub>28s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/shepherd-campaign.json.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/shepherd-campaign.json (lines 1--1)

<details>
<summary>16 lines</summary>

```
{
  "schemaVersion": 1,
  "campaignId": "34dfbae2-dbd9-4702-ad9a-808b93224026",
  "campaignIssueNumber": 1,
  "campaignShortname": "arrival-deadline-control",
  "repository": "edburns/dd-3061974-05-cargotracker-linux",
  "baseBranch": "experiment/shepherd-control",
  "lessonPropagation": "off",
  "campaignMetadataDirectory": "1-arrival-deadline-control-remove-before-merge",
  "lessonsFile": "campaign-lessons.md",
  "createdBy": {
    "shepherdTaskVersion": "1.0.2",
    "stageOutcomeProtocolVersion": 1
  },
  "createdAt": "2026-09-10T17:34:30Z"
}
```

</details>


---

<sub>28s</sub>

### `glob`

**1-arrival-deadline-control-remove-before-merge/**/***

"1-arrival-deadline-control-remove-before-merge/**/*"

<details>
<summary>11 matches</summary>

```
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/.stage20-after.111688
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/shepherd-campaign.json
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/.stage20-before.111688
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/shepherd-test-experiment.json
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/campaign-lessons.md
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/20260910-1734-invoke-shepherd-task-20-create-issues-from-plan-skill.sh
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/20260910-1734-invoke-shepherd-task-20-create-issues-from-plan-skill.md
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/create-issues-otel-20260910-1734.jsonl
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/pre-creation-children.json
/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/create-issues-session-20260910-1734.jsonl
```

</details>


---

<sub>28s</sub>

### `rg`

**^#{2,4} |^\*\*Resolution:\*\*|^Resolution:**

"^#{2,4} |^\*\*Resolution:\*\*|^Resolution:" in *.md (1-arrival-deadline-control-remove-before-merge)

<details>
<summary>37 matches</summary>

```
[grep content: 35 matches across 1 file(s) under /home/edburns/workareas]

dd (35 match(es)):
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:22:## Goal
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:35:### User-visible acceptance behavior
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:62:### Domain acceptance behavior
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:77:### Hard scope constraints
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:95:## Completed phases
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:97:### Phase 1 ✅ — Establish a runnable feature-absent baseline
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:109:### Phase 2 ✅ — Verify the before and after user experience
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:123:## Phase 3 — Ignorance reduction: questions to answer before writing code
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:129:### 3.1 — Which cargos expose the edit operation?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:155:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:162:### 3.2 — What is the exact domain mutation?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:201:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:211:### 3.3 — What should happen to an existing itinerary and delivery state?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:237:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:245:### 3.4 — What type crosses the facade boundary?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:273:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:281:### 3.5 — How is the DTO's formatted deadline converted for editing?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:311:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:321:### 3.6 — Which JSF bean scopes and interaction pattern should be used?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:351:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:360:### 3.7 — What is the dynamic-dialog contract?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:401:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:412:### 3.8 — What date validation is required?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:430:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:438:### 3.9 — How will the feature be tested on the prepared historical baseline?
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:473:**Resolution:**
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:488:## Phase 4 — Implementation (five serial issues)
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:493:### 4.1 — Issue 1: Add the application-layer deadline change operation
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:561:### 4.2 — Issue 2: Expose deadline changes through the booking facade
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:616:### 4.3 — Issue 3: Implement the deadline editor backing model
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:695:### 4.4 — Issue 4: Implement the PrimeFaces deadline dialog
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:792:### 4.5 — Issue 5: Integrate deadline editing into the Administration dashboard
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:876:## Phase 5 — Documentation and implementation handoff
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md:895:## Cross-cutting concerns
  3061974- 05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/campaign-lessons.md:6:## Validated lessons
```

</details>


---

<sub>33s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/add-change-arrival-deadline-feature-ignorance-reduction-plan.md (lines 680-940)

<details>
<summary>171 lines</summary>

````

- `load()` requests the correct tracking ID;
- `load()` converts an `MM/dd/yyyy` DTO date into the editable `Date`;
- `changeArrivalDeadline()` delegates the selected date and tracking ID;
- a malformed DTO deadline is surfaced rather than converted to null;
- a null selected date is rejected.

Do not add a mocking framework solely for these tests.

**Gating criteria**

- The bean is serializable and uses the established CDI/JSF annotations.
- The bean references only facade DTOs, not domain model classes.
- `./mvnw clean package -Popenliberty` succeeds on JDK 17.

### 4.4 — Issue 4: Implement the PrimeFaces deadline dialog

**What to build**

Add the session-scoped dialog launcher and the dynamic dialog view. The dialog
must work when addressed directly with a `trackingId` query parameter, but it
is not yet linked from the dashboard in this issue.

**Files to create**

- `src/main/java/org/eclipse/cargotracker/interfaces/booking/web/ChangeArrivalDeadlineDateDialog.java`
- `src/main/webapp/admin/dialogs/changeArrivalDeadlineDate.xhtml`

**Launcher requirements**

Use:

```java
@ManagedBean(name = "changeArrivalDeadlineDateDialog")
@SessionScoped
```

Implement:

- `showDialog(String trackingId)`
- `handleReturn(SelectEvent event)`
- `cancel()`

`showDialog(...)` must:

- set the options documented in Question 3.7;
- pass `trackingId` as a dynamic-dialog request parameter;
- open `/admin/dialogs/changeArrivalDeadlineDate.xhtml`.

`cancel()` must close the dialog without invoking the facade.

**XHTML requirements**

The page title must be:

```xhtml
<title>Change Deadline</title>
```

Place metadata directly beneath the root `<html>` element and before
`<h:head>`:

```xhtml
<f:metadata>
    <f:viewParam name="trackingId"
                 value="#{changeArrivalDeadlineDate.trackingId}"/>
    <f:viewAction action="#{changeArrivalDeadlineDate.load}"/>
</f:metadata>
```

The form must display:

- `Origin:` and `changeArrivalDeadlineDate.cargo.originName`;
- `Destination:` and
  `changeArrivalDeadlineDate.cargo.finalDestinationName`;
- `Deadline:` and a `p:datePicker` bound to
  `changeArrivalDeadlineDate.arrivalDeadlineDate`;
- **Cancel**, invoking
  `changeArrivalDeadlineDateDialog.cancel()`;
- **Update**, invoking
  `changeArrivalDeadlineDate.changeArrivalDeadline()`.

The date picker must require a value. The Update action must reload or refresh
the calling Administration view after a successful dialog close, following the
existing destination-dialog behavior.

**Runtime tests**

With the application running, request:

```text
http://localhost:8080/cargo-tracker/admin/dialogs/changeArrivalDeadlineDate.xhtml?trackingId=DEF789
```

Verify:

- HTTP 200;
- title is **Change Deadline**;
- origin and destination render;
- the existing deadline is selected;
- no `TagException`, `Parent UIComponent`, `FacesException`, or server error is
  present;
- Cancel does not change the persisted deadline;
- Update changes the deadline.

**Gating criteria**

- `./mvnw clean package -Popenliberty liberty:run` succeeds on JDK 17.
- Direct dialog loading and both actions work.
- Destination editing continues to work.
- Stop Liberty cleanly before completing the issue.

### 4.5 — Issue 5: Integrate deadline editing into the Administration dashboard

**What to build**

Replace the plain deadline text in the Not Routed Cargo table with the
PrimeFaces command-link affordance that opens the completed dialog and refreshes
the table after return.

**File to modify**

- `src/main/webapp/admin/tables/listNotRouted.xhtml`

**Required UI shape**

Within the existing Deadline column, add a `p:commandLink` that:

- calls
  `changeArrivalDeadlineDateDialog.showDialog(cargoNotRouted.trackingId)`;
- retains the displayed
  `cargoNotRouted.arrivalDeadlineDate`;
- adds the existing Font Awesome edit icon style;
- uses a stable component ID such as `arrivalDeadlineToUpdate`;
- listens for `dialogReturn`;
- invokes
  `changeArrivalDeadlineDateDialog.handleReturn`;
- updates `tableNotRouted`;
- provides the tooltip:
  `Click to change cargo arrival deadline date.`

Follow the adjacent Destination column's established structure and styling. Do
not alter tracking-ID routing or destination editing.

**End-to-end acceptance test**

1. Start from a clean build on JDK 17:

   ```bash
   ./mvnw clean package -Popenliberty liberty:run
   ```

2. Confirm the home page returns HTTP 200.
3. Open Administration and locate `DEF789`.
4. Record the original deadline.
5. Confirm the deadline now has an edit icon and tooltip.
6. Open the deadline dialog.
7. Confirm origin and destination identify the same cargo.
8. Choose a visibly different date.
9. Press **Update**.
10. Confirm the dialog closes and the Not Routed Cargo table refreshes.
11. Confirm the table shows the selected date.
12. Reload the browser and confirm the selected date remains.
13. Reopen the dialog and confirm the editor initializes to the changed date.
14. Press **Cancel** and confirm no additional change occurs.
15. Verify the Destination edit dialog still opens.
16. Verify selecting `DEF789` for routing still loads without an error page.

**Log acceptance**

The final run must contain none of:

- `<f:metadata> Parent UIComponent`;
- `TagException`;
- `VerifyError`;
- `FacesException`;
- `CWWKZ0002E` or `CWWKZ0003E`;
- recurring batch authorization failures;
- new FFDC files attributable to this feature.

Transient JMS activation-order warnings are acceptable only if all message
endpoints subsequently activate, as established by the prepared baseline.

**Final regression and scope checks**

- `./mvnw clean package -Popenliberty` succeeds.
- The existing test sources and the new deadline test compile.
- No Java EE namespace migration occurred.
- No Open Liberty, Derby, Jackson, JSF metadata, batch authorization, or REST
  compatibility fix from the starting commit was reverted.
- The feature affects only the intended application, facade, web, dialog,
  table, and test surfaces.
- Stop Liberty cleanly.

---

## Phase 5 — Documentation and implementation handoff

- Update `README.md` only if user-facing Administration capabilities are
  enumerated there; add one concise sentence that administrators can change an
  unrouted cargo's arrival deadline.
- Record the exact JDK 17 run command in the final issue or pull-request
  description:

  ```bash
  ./mvnw clean package -Popenliberty liberty:run
  ```

- Include `DEF789` and the before/after deadline values in the acceptance
  evidence.
- State explicitly that data is in-memory and resets when the application is
  rebuilt/restarted.

---

## Cross-cutting concerns

| Concern | Required handling |
|---------|--------------------|
| Domain-driven design | Mutate the aggregate through `Cargo.specifyNewRoute(...)`; do not bypass it with persistence-level field updates. |
| Layering | Web bean → booking facade → booking service → cargo repository. |
| Date handling | Use `java.util.Date` at service/facade boundaries and `MM/dd/yyyy` for the date-only editor representation. |
| Time zones | Acceptance compares the calendar date displayed by the application; do not introduce a new timezone policy in this feature. |
| Error handling | Do not silently swallow parse, lookup, validation, or persistence failures. A failed update must not look successful. |
| JSF compatibility | Keep `<f:metadata>` at view-root scope for MyFaces. |
| Bean lifecycle | Editor is CDI `@ViewScoped`; dynamic-dialog launcher mirrors the existing session-scoped JSF managed bean. |
| Accessibility | Preserve visible labels; the date editor must have an associated label and validation feedback. |
| Backward compatibility | Existing destination editing, routing, tracking, REST, messaging, batch, and startup behavior must remain intact. |
| Test discipline | Add tests before production code where practical; every issue must preserve all prior gates. |
| Experiment integrity | Implement from this specification starting at `9b9f311b2a3a2854bdac947593950d9edb6bca7d`; do not cherry-pick or inspect feature-bearing commits. |
````

</details>


---

<sub>33s</sub>

### `view`

**view the file at /home/edburns/.copilot/plugins/shepherd-task/scripts/validate-stage20-drafts.sh.**

/home/edburns/.copilot/plugins/shepherd-task/scripts/validate-stage20-drafts.sh (lines 1--1)

<details>
<summary>66 lines</summary>

```
#!/usr/bin/env bash
# shepherd-task-version: 1.0.2

set -euo pipefail

[[ $# -eq 3 ]] || {
    echo "Usage: $0 <body-directory> <expected-count> <off|campaign>" >&2
    exit 1
}

body_directory="$1"
expected_count="$2"
lesson_propagation="$3"

[[ -d "$body_directory" ]] || {
    echo "Stage-20 body directory does not exist: $body_directory" >&2
    exit 1
}
[[ "$expected_count" =~ ^[1-9][0-9]*$ ]] || {
    echo "Expected count must be a positive integer." >&2
    exit 1
}
[[ "$lesson_propagation" == "off" || "$lesson_propagation" == "campaign" ]] || {
    echo "Lesson propagation must be off or campaign." >&2
    exit 1
}

body_files=()
while IFS= read -r -d '' body_file; do
    body_files+=("$body_file")
done < <(
    find "$body_directory" -maxdepth 1 -type f -name '*-body.md' \
        ! -name '*-observed-body.md' -print0
)
[[ ${#body_files[@]} -eq $expected_count ]] || {
    echo "Expected $expected_count persisted stage-20 body files; found ${#body_files[@]}." >&2
    exit 1
}

required_headings=(
    '## Campaign context and required reading'
    '## Branch and execution order'
    '## Implement'
    '## Completion gates'
    '## Out of scope'
)

for body_file in "${body_files[@]}"; do
    [[ -s "$body_file" && "$(awk 'END { print NR }' "$body_file")" -gt 1 ]] || {
        echo "Persisted issue body must contain physical Markdown lines: $body_file" >&2
        exit 1
    }
    first_nonblank="$(awk 'NF { print; exit }' "$body_file")"
    [[ "$first_nonblank" =~ ^##[[:space:]]+[^[:space:]] ]] || {
        echo "Persisted issue body must begin with a level-two heading: $body_file" >&2
        exit 1
    }
    for heading in "${required_headings[@]}"; do
        grep -Fqx -- "$heading" "$body_file" || {
            echo "Persisted issue body is missing the physical heading '$heading': $body_file" >&2
            exit 1
        }
    done
    if [[ "$lesson_propagation" == "campaign" ]]; then
        grep -Fqx -- '## Campaign lessons (REQUIRED)' "$body_file" || {
            echo "Treatment issue body is missing its campaign-lessons heading: $body_file" >&2
            exit 1
        }
    elif grep -Fqx -- '## Campaign lessons (REQUIRED)' "$body_file"; then
        echo "Control issue body unexpectedly contains a campaign-lessons heading: $body_file" >&2
        exit 1
    fi
done
```

</details>


---

<sub>33s</sub>

### `view`

**view the file at /home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh.**

/home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh (lines 1-220)

<details>
<summary>131 lines</summary>

```
#!/usr/bin/env bash
# shepherd-task-version: 1.0.2
set -euo pipefail

fail() {
    printf 'verify-github-issue-body: %s\n' "$*" >&2
    exit 1
}

[[ $# -ge 3 && $# -le 6 ]] ||
    fail "usage: $0 OWNER/REPO ISSUE_NUMBER EXPECTED_BODY_PATH [MAX_ATTEMPTS] [DELAY_SECONDS] [DIAGNOSTIC_PATH]"

repository="$1"
issue_number="$2"
expected_body_path="$3"
max_attempts="${4:-6}"
delay_seconds="${5:-5}"
diagnostic_path="${6:-}"
gh_command="${GH_COMMAND:-gh}"

[[ "$repository" =~ ^[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+$ ]] ||
    fail "invalid repository: $repository"
[[ "$issue_number" =~ ^[1-9][0-9]*$ ]] ||
    fail "invalid issue number: $issue_number"
[[ "$max_attempts" =~ ^[1-9][0-9]*$ ]] ||
    fail "MAX_ATTEMPTS must be a positive integer"
[[ "$delay_seconds" =~ ^[0-9]+$ ]] ||
    fail "DELAY_SECONDS must be a non-negative integer"
[[ -f "$expected_body_path" ]] ||
    fail "expected issue body file not found: $expected_body_path"

temp_directory="$(mktemp -d)"
trap 'rm -rf "$temp_directory"' EXIT
response_path="$temp_directory/response.json"
actual_path="$temp_directory/actual.txt"
actual_normalized="$temp_directory/actual-normalized.txt"
expected_normalized="$temp_directory/expected-normalized.txt"

normalize_file() {
    jq -Rsj 'gsub("\r\n|\r"; "\n")' "$1" >"$2"
}

equivalent_files() {
    local actual="$1"
    local expected="$2"
    local candidate="$temp_directory/candidate.txt"

    cmp -s -- "$actual" "$expected" && return 0
    cp "$actual" "$candidate"
    printf '\n' >>"$candidate"
    cmp -s -- "$candidate" "$expected" && return 0
    cp "$expected" "$candidate"
    printf '\n' >>"$candidate"
    cmp -s -- "$actual" "$candidate"
}

sha256_file() {
    if command -v sha256sum >/dev/null 2>&1; then
        sha256sum "$1" | awk '{print $1}'
    else
        shasum -a 256 "$1" | awk '{print $1}'
    fi
}

write_diagnostic() {
    local reason="$1"
    local attempts="$2"
    [[ -n "$diagnostic_path" ]] || return 0

    mkdir -p "$(dirname "$diagnostic_path")"
    local expected_length actual_length expected_hash actual_hash first_offset
    expected_length="$(wc -c <"$expected_normalized" | tr -d ' ')"
    actual_length="$(wc -c <"$actual_normalized" | tr -d ' ')"
    expected_hash="$(sha256_file "$expected_normalized")"
    actual_hash="$(sha256_file "$actual_normalized")"
    first_offset="$( (cmp -l -- "$actual_normalized" "$expected_normalized" 2>/dev/null || true) | awk 'NR == 1 { print $1 - 1 }')"
    [[ -n "$first_offset" ]] || first_offset="null"

    jq -n \
        --arg repository "$repository" \
        --argjson issueNumber "$issue_number" \
        --arg endpoint "repos/$repository/issues/$issue_number" \
        --argjson attempts "$attempts" \
        --arg observedAt "$(date -u +%Y-%m-%dT%H:%M:%SZ)" \
        --arg reason "$reason" \
        --argjson expectedLength "$expected_length" \
        --argjson actualLength "$actual_length" \
        --arg expectedSha256 "$expected_hash" \
        --arg actualSha256 "$actual_hash" \
        --argjson firstDifferenceOffset "$first_offset" \
        '{
            schemaVersion: 1,
            repository: $repository,
            issueNumber: $issueNumber,
            endpoint: $endpoint,
            attempts: $attempts,
            observedAt: $observedAt,
            reason: $reason,
            expectedLength: $expectedLength,
            actualLength: $actualLength,
            expectedSha256: $expectedSha256,
            actualSha256: $actualSha256,
            firstDifferenceOffset: $firstDifferenceOffset
        }' >"$diagnostic_path"
}

normalize_file "$expected_body_path" "$expected_normalized"
last_reason=""

for ((attempt = 1; attempt <= max_attempts; attempt++)); do
    set +e
    "$gh_command" api "repos/$repository/issues/$issue_number" >"$response_path" 2>"$temp_directory/error.txt"
    exit_code=$?
    set -e

    if [[ $exit_code -ne 0 ]]; then
        last_reason="GitHub REST request failed with exit code $exit_code."
        error_message="$(cat "$temp_directory/error.txt")"
        authentication_error_pattern='HTTP[[:space:]]+(401|403)|[Aa]uthentication|[Nn]ot[[:space:]]authorized|[Rr]esource[[:space:]]not[[:space:]]accessible'
        if [[ "$error_message" =~ $authentication_error_pattern ]]; then
            : >"$actual_normalized"
            write_diagnostic "$last_reason" "$attempt"
            fail "unable to fetch issue #$issue_number from $repository: $error_message"
        fi
    elif ! jq -e 'type == "object" and has("body")' "$response_path" >/dev/null 2>&1; then
        last_reason="GitHub REST response was invalid JSON."
    else
        jq -j '.body // ""' "$response_path" >"$actual_path"
        normalize_file "$actual_path" "$actual_normalized"
        if equivalent_files "$actual_normalized" "$expected_normalized"; then
            cat "$response_path"
            exit 0
        fi
        last_reason="GitHub issue body differs from the persisted draft."
    fi

    if [[ $attempt -lt $max_attempts ]]; then
        printf 'verify-github-issue-body: %s Retrying issue #%s body verification (%s/%s).\n' \
            "$last_reason" "$issue_number" "$attempt" "$max_attempts" >&2
        ((delay_seconds > 0)) && sleep "$delay_seconds"
    fi
done

[[ -f "$actual_normalized" ]] || : >"$actual_normalized"
write_diagnostic "$last_reason" "$max_attempts"
diagnostic_suffix=""
[[ -z "$diagnostic_path" ]] || diagnostic_suffix=" Diagnostic: $diagnostic_path"
fail "issue #$issue_number body verification failed after $max_attempts attempts. $last_reason$diagnostic_suffix"
```

</details>


---

<sub>33s</sub>

### `view`

**view the file at /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/campaign-lessons.md.**

/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/campaign-lessons.md (lines 1--1)

<details>
<summary>5 lines</summary>

```
# Campaign lessons

This file contains validated, reusable lessons for subsequent issues in this campaign.
The issue specification and repository instructions remain authoritative.

## Validated lessons

No validated lessons have been recorded yet.
```

</details>


---

<sub>40s</sub>

### Copilot

All nine implementation questions have substantive resolution blocks; none remains unresolved. The five direct Phase 4 headings exactly match the expected count, and the campaign manifest matches the supplied ID and `off` lesson mode.

---

<sub>1m 32s</sub>

### `apply_patch`

<details>
<summary>Arguments</summary>

````json
"*** Begin Patch\n*** Add File: 1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/01-4.1-body.md\n+## Campaign context and required reading\n+\n+On the `experiment/shepherd-control` branch, the directory `1-arrival-deadline-control-remove-before-merge` contains the plan (`add-change-arrival-deadline-feature-ignorance-reduction-plan.md`) and supporting resources (diagrams, decision records). Spike subdirectories are research artifacts — read the plan's Resolution sections for findings, not the spike source code.\n+\n+Read the entire plan before working. Then re-read these exact sections:\n+\n+- `## Goal`, including `### Domain acceptance behavior` and `### Hard scope constraints`\n+- `### 3.2 — What is the exact domain mutation?`\n+- `### 3.3 — What should happen to an existing itinerary and delivery state?`\n+- `### 3.8 — What date validation is required?`\n+- `### 3.9 — How will the feature be tested on the prepared historical baseline?`\n+- `### 4.1 — Issue 1: Add the application-layer deadline change operation`\n+- `## Cross-cutting concerns`\n+\n+The resolved design is concrete: load the cargo, replace its `RouteSpecification` with one containing the existing origin, existing destination, and supplied `Date`, apply it through `Cargo.specifyNewRoute(...)`, and store the aggregate. Preserve the assigned itinerary and let the aggregate recalculate delivery and routing state; in the established sequential test the cargo remains `MISROUTED`. Require a non-null deadline but add no new chronological rule. Research established that the historical Arquillian suite remains tied to remote Payara and is compiled but skipped by the prepared Open Liberty build; do not modernize that runtime or add a mocking dependency.\n+\n+## Branch and execution order\n+\n+Target `experiment/shepherd-control` from remote `origin`. This is task 1 of 5. The tasks are assigned, completed, and merged serially in plan order; do not start work until this issue is assigned, and do not begin task 2 until this task's gates pass and its PR is merged.\n+\n+Begin from the prepared campaign baseline. Do not inspect, copy, or cherry-pick feature-bearing commits or spike code. Implement the production behavior from the plan and this specification.\n+\n+## Implement\n+\n+Modify only:\n+\n+- `src/main/java/org/eclipse/cargotracker/application/BookingService.java`\n+- `src/main/java/org/eclipse/cargotracker/application/internal/DefaultBookingService.java`\n+- `src/test/java/org/eclipse/cargotracker/application/BookingServiceTest.java`\n+\n+Add this application API:\n+\n+```java\n+void changeDeadline(TrackingId trackingId, Date deadline);\n+```\n+\n+Implement it by:\n+\n+1. Calling `cargoRepository.find(trackingId)`.\n+2. Reading the current destination from `cargo.getRouteSpecification().getDestination()`.\n+3. Creating a replacement `RouteSpecification` from `cargo.getOrigin()`, that current destination, and `deadline`.\n+4. Calling `cargo.specifyNewRoute(routeSpecification)`.\n+5. Calling `cargoRepository.store(cargo)`.\n+6. Logging the tracking ID and new deadline at `Level.INFO` in the style of `changeDestination(...)`.\n+\n+Write the test first. Append sequential `testChangeDeadline()` after `testChangeDestination()`. Advance the original test deadline by one month, invoke the new operation, reload through `Cargo.findByTrackingId`, and assert:\n+\n+- origin is still Chicago and destination is still Helsinki;\n+- the stored deadline is the same calendar day as requested;\n+- the assigned itinerary is unchanged;\n+- transport status is `NOT_RECEIVED`;\n+- last known location is `Location.UNKNOWN`;\n+- current voyage is `Voyage.NONE`;\n+- the cargo is not misdirected;\n+- ETA is `Delivery.ETA_UNKOWN`;\n+- next expected activity is `Delivery.NO_ACTIVITY`;\n+- it is not unloaded at destination;\n+- routing status remains `MISROUTED`.\n+\n+Use the repository's existing Java EE 7, Java 7 source-level, JUnit, Arquillian, logging, and date-comparison patterns.\n+\n+## Completion gates\n+\n+- The new sequential test source compiles and completely specifies preserved and recalculated state, including itinerary identity and `MISROUTED`.\n+- `./mvnw clean package -Popenliberty` succeeds on JDK 17.\n+- Existing application-layer behavior and tests remain unchanged except for the new API, implementation, and appended test.\n+- The diff contains no web, facade, REST, Liberty, persistence-configuration, namespace-migration, or dependency changes.\n+\n+## Out of scope\n+\n+Do not add mutable deadline setters to domain objects, mutate persistence state behind the aggregate, change origin or destination, clear or replace the itinerary, reroute cargo, add facade or UI code, modernize Arquillian/Payara, add test libraries, migrate `javax.*`, or alter unrelated booking, routing, messaging, batch, REST, Derby, or Liberty behavior.\n*** Add File: 1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/02-4.2-body.md\n+## Campaign context and required reading\n+\n+On the `experiment/shepherd-control` branch, the directory `1-arrival-deadline-control-remove-before-merge` contains the plan (`add-change-arrival-deadline-feature-ignorance-reduction-plan.md`) and supporting resources (diagrams, decision records). Spike subdirectories are research artifacts — read the plan's Resolution sections for findings, not the spike source code.\n+\n+Read the entire plan before working. Then re-read these exact sections:\n+\n+- `## Goal`, including `### Domain acceptance behavior` and `### Hard scope constraints`\n+- `### 3.2 — What is the exact domain mutation?`\n+- `### 3.4 — What type crosses the facade boundary?`\n+- `### 3.9 — How will the feature be tested on the prepared historical baseline?`\n+- `### 4.2 — Issue 2: Expose deadline changes through the booking facade`\n+- `## Cross-cutting concerns`\n+\n+The resolved facade contract uses a `String` tracking ID and the same `java.util.Date` value supplied by the caller. Convert only the identifier to `new TrackingId(trackingId)` and delegate once to the application service. Do not expose `TrackingId`, `Cargo`, or `RouteSpecification` to JSF clients, introduce a command DTO or formatted-string service parameter, or duplicate repository work. Research established that focused container-free tests should use hand-written fakes and no new mocking dependency.\n+\n+## Branch and execution order\n+\n+Target `experiment/shepherd-control` from remote `origin`. This is task 2 of 5. The tasks are assigned, completed, and merged serially in plan order. Start only after this issue is assigned and task 1 has passed its gates and merged; do not begin task 3 until this task passes and merges.\n+\n+The application operation from task 1 is a prerequisite and must remain intact. Do not inspect, copy, or cherry-pick feature-bearing commits or spike code.\n+\n+## Implement\n+\n+Modify:\n+\n+- `src/main/java/org/eclipse/cargotracker/interfaces/booking/facade/BookingServiceFacade.java`\n+- `src/main/java/org/eclipse/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacade.java`\n+\n+Optionally add:\n+\n+- `src/test/java/org/eclipse/cargotracker/interfaces/booking/facade/internal/DefaultBookingServiceFacadeTest.java`\n+\n+Add:\n+\n+```java\n+void changeDeadline(String trackingId, Date arrivalDeadline);\n+```\n+\n+The implementation must delegate exactly as follows:\n+\n+```java\n+bookingService.changeDeadline(\n+        new TrackingId(trackingId),\n+        arrivalDeadline);\n+```\n+\n+If practical in the existing test structure, add a container-free JUnit test with a hand-written `BookingService` fake or spy proving the tracking string becomes an equivalent `TrackingId`, the same `Date` object/value reaches the application service, delegation occurs exactly once, and no repository operation is duplicated in the facade.\n+\n+## Completion gates\n+\n+- Existing facade consumers compile.\n+- `./mvnw clean package -Popenliberty` succeeds on JDK 17.\n+- The task 1 application API and sequential application test remain unchanged and compile.\n+- A focused test, if added, demonstrates exact one-time delegation without a new dependency.\n+- The facade implementation contains no cargo loading, domain mutation, repository access, date parsing, JSF, or PrimeFaces logic.\n+\n+## Out of scope\n+\n+Do not change the application-layer semantics from task 1, load or mutate `Cargo` in the facade, call `CargoRepository`, parse formatted dates, introduce DTOs, add UI code, add mocking libraries, migrate Java EE namespaces, or alter unrelated facade endpoints and runtime configuration.\n*** Add File: 1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/03-4.3-body.md\n+## Campaign context and required reading\n+\n+On the `experiment/shepherd-control` branch, the directory `1-arrival-deadline-control-remove-before-merge` contains the plan (`add-change-arrival-deadline-feature-ignorance-reduction-plan.md`) and supporting resources (diagrams, decision records). Spike subdirectories are research artifacts — read the plan's Resolution sections for findings, not the spike source code.\n+\n+Read the entire plan before working. Then re-read these exact sections:\n+\n+- `## Goal`, including `### User-visible acceptance behavior` and `### Hard scope constraints`\n+- `### 3.4 — What type crosses the facade boundary?`\n+- `### 3.5 — How is the DTO's formatted deadline converted for editing?`\n+- `### 3.6 — Which JSF bean scopes and interaction pattern should be used?`\n+- `### 3.8 — What date validation is required?`\n+- `### 3.9 — How will the feature be tested on the prepared historical baseline?`\n+- `### 4.3 — Issue 3: Implement the deadline editor backing model`\n+- `## Cross-cutting concerns`\n+\n+Resolved findings: the bean stays behind `BookingServiceFacade`; it loads `CargoRoute`, not domain objects. Parse the DTO's date-only value using a per-load `new SimpleDateFormat(\"MM/dd/yyyy\")`, preserving the existing DTO contract and avoiding shared mutable formatters. The editor is serializable CDI `@Named @ViewScoped`. Parsing and null-input failures must be visible rather than becoming null or success-shaped behavior. No new minimum, future, old-deadline, itinerary-date, or timezone policy is allowed.\n+\n+## Branch and execution order\n+\n+Target `experiment/shepherd-control` from remote `origin`. This is task 3 of 5. The tasks are assigned, completed, and merged serially in plan order. Start only after assignment and after tasks 1 and 2 have passed and merged; task 4 must wait for this task.\n+\n+Use the facade API delivered by task 2. Do not inspect, copy, or adapt spike code or feature-bearing commits.\n+\n+## Implement\n+\n+Create:\n+\n+- `src/main/java/org/eclipse/cargotracker/interfaces/booking/web/ChangeArrivalDeadlineDate.java`\n+\n+Use this shape:\n+\n+```java\n+@Named\n+@ViewScoped\n+public class ChangeArrivalDeadlineDate implements Serializable {\n+    private static final long serialVersionUID = 1L;\n+\n+    private String trackingId;\n+    private CargoRoute cargo;\n+    private Date arrivalDeadlineDate;\n+\n+    @Inject\n+    private BookingServiceFacade bookingServiceFacade;\n+}\n+```\n+\n+Provide `getTrackingId()`, `setTrackingId(String)`, `getCargo()`, `getArrivalDeadlineDate()`, `setArrivalDeadlineDate(Date)`, `load()`, and `changeArrivalDeadline()`.\n+\n+`load()` must call `bookingServiceFacade.loadCargoForRouting(trackingId)`, store the returned DTO, parse `cargo.getArrivalDeadlineDate()` with a per-load `SimpleDateFormat(\"MM/dd/yyyy\")`, and store the parsed date. Surface malformed DTO data as a clear application/view error consistent with existing JSF behavior; do not swallow, print, or convert the failure to null.\n+\n+`changeArrivalDeadline()` must reject a null selected date, call `bookingServiceFacade.changeDeadline(trackingId, arrivalDeadlineDate)`, and only after successful delegation call `PrimeFaces.current().dialog().closeDynamic(\"DONE\")`. A failed facade call must leave the dialog open and expose the failure.\n+\n+Where practical, add a container-free JUnit test with a hand-written facade fake proving correct tracking-ID loading, `MM/dd/yyyy` conversion, selected-date delegation, malformed-date failure, null-date rejection, and no close-before-success behavior. Do not add a mocking framework.\n+\n+## Completion gates\n+\n+- The bean is serializable and uses the repository's established CDI `@Named` and JSF `@ViewScoped` annotations.\n+- It imports facade DTOs but no domain model or repository classes.\n+- Date parsing is local per load and errors are explicit.\n+- Null input is rejected and facade failure cannot look successful.\n+- `./mvnw clean package -Popenliberty` succeeds on JDK 17.\n+- Task 1 and task 2 contracts and tests remain intact.\n+\n+## Out of scope\n+\n+Do not create the launcher or XHTML, modify dashboard tables, query domain objects or repositories, broaden `CargoRoute`, add shared `SimpleDateFormat`, add chronological or timezone policy, add dependencies, modernize JSF/Java EE, or alter existing destination editing.\n*** Add File: 1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/04-4.4-body.md\n+## Campaign context and required reading\n+\n+On the `experiment/shepherd-control` branch, the directory `1-arrival-deadline-control-remove-before-merge` contains the plan (`add-change-arrival-deadline-feature-ignorance-reduction-plan.md`) and supporting resources (diagrams, decision records). Spike subdirectories are research artifacts — read the plan's Resolution sections for findings, not the spike source code.\n+\n+Read the entire plan before working. Then re-read these exact sections:\n+\n+- `## Goal`, including `### User-visible acceptance behavior` and `### Hard scope constraints`\n+- `### Phase 2 ✅ — Verify the before and after user experience`\n+- `### 3.5 — How is the DTO's formatted deadline converted for editing?`\n+- `### 3.6 — Which JSF bean scopes and interaction pattern should be used?`\n+- `### 3.7 — What is the dynamic-dialog contract?`\n+- `### 3.8 — What date validation is required?`\n+- `### 4.4 — Issue 4: Implement the PrimeFaces deadline dialog`\n+- `## Cross-cutting concerns`\n+\n+Research established the existing Change Destination dynamic-dialog lifecycle as the compatibility pattern. The launcher is a serializable session-scoped JSF managed bean; the editor delivered in task 3 is CDI view-scoped. Open `/admin/dialogs/changeArrivalDeadlineDate.xhtml` with one `trackingId` parameter, modal and draggable `true`, resizable `false`, width `410`, and height `280`. Successful update returns `\"DONE\"` and Cancel returns `\"\"`. MyFaces requires `<f:metadata>` directly beneath the root `<html>` and before `<h:head>`/`<h:body>`. The date is required but has no new chronological restriction.\n+\n+## Branch and execution order\n+\n+Target `experiment/shepherd-control` from remote `origin`. This is task 4 of 5. The tasks are assigned, completed, and merged serially in plan order. Start only after assignment and after tasks 1–3 pass and merge; task 5 waits for this task.\n+\n+Implement the established behavior from scratch using production dependencies. Do not read or copy spike code or feature-bearing commits.\n+\n+## Implement\n+\n+Create:\n+\n+- `src/main/java/org/eclipse/cargotracker/interfaces/booking/web/ChangeArrivalDeadlineDateDialog.java`\n+- `src/main/webapp/admin/dialogs/changeArrivalDeadlineDate.xhtml`\n+\n+The launcher must be serializable and use:\n+\n+```java\n+@ManagedBean(name = \"changeArrivalDeadlineDateDialog\")\n+@SessionScoped\n+```\n+\n+Implement `showDialog(String trackingId)`, `handleReturn(SelectEvent event)`, and `cancel()`. `showDialog` must pass the single tracking ID as `Map<String, List<String>>`, configure the exact resolved options, and open the resolved path. `cancel()` closes with an empty string and does not invoke the facade. Preserve the existing destination-dialog return-handling behavior.\n+\n+The XHTML title is `Change Deadline`. Place this before `h:head`:\n+\n+```xhtml\n+<f:metadata>\n+    <f:viewParam name=\"trackingId\"\n+                 value=\"#{changeArrivalDeadlineDate.trackingId}\"/>\n+    <f:viewAction action=\"#{changeArrivalDeadlineDate.load}\"/>\n+</f:metadata>\n+```\n+\n+Display read-only `Origin:` from `changeArrivalDeadlineDate.cargo.originName` and `Destination:` from `changeArrivalDeadlineDate.cargo.finalDestinationName`. Provide an associated `Deadline:` label, validation feedback, and a required `p:datePicker` bound to `changeArrivalDeadlineDate.arrivalDeadlineDate`. Add Cancel invoking `changeArrivalDeadlineDateDialog.cancel()` and Update invoking `changeArrivalDeadlineDate.changeArrivalDeadline()`.\n+\n+Run the application and directly request:\n+\n+`http://localhost:8080/cargo-tracker/admin/dialogs/changeArrivalDeadlineDate.xhtml?trackingId=DEF789`\n+\n+Verify HTTP 200, title, context values, selected existing deadline, Cancel preserving the value, Update changing it, and no `TagException`, `Parent UIComponent`, `FacesException`, or server error. Verify destination editing still works.\n+\n+## Completion gates\n+\n+- `./mvnw clean package -Popenliberty liberty:run` succeeds on JDK 17.\n+- Direct dialog loading and both actions work for `DEF789`.\n+- Metadata is at view-root scope and the exact dimensions, parameter, close values, bean scopes, labels, required validation, and error behavior are present.\n+- Existing destination editing remains functional.\n+- Liberty is stopped cleanly before completion.\n+- No dashboard integration is included yet.\n+\n+## Out of scope\n+\n+Do not modify `listNotRouted.xhtml`, add another navigation page or inline editor, add chronological validation, close successfully after a facade failure, move metadata into the body, alter task 1–3 contracts, add dependencies, migrate namespaces, or redesign existing dialogs.\n*** Add File: 1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/05-4.5-body.md\n+## Campaign context and required reading\n+\n+On the `experiment/shepherd-control` branch, the directory `1-arrival-deadline-control-remove-before-merge` contains the plan (`add-change-arrival-deadline-feature-ignorance-reduction-plan.md`) and supporting resources (diagrams, decision records). Spike subdirectories are research artifacts — read the plan's Resolution sections for findings, not the spike source code.\n+\n+Read the entire plan before working. Then re-read these exact sections:\n+\n+- `## Goal`, including `### User-visible acceptance behavior`, `### Domain acceptance behavior`, and `### Hard scope constraints`\n+- `### Phase 2 ✅ — Verify the before and after user experience`\n+- `### 3.1 — Which cargos expose the edit operation?`\n+- `### 3.6 — Which JSF bean scopes and interaction pattern should be used?`\n+- `### 3.7 — What is the dynamic-dialog contract?`\n+- `### 3.8 — What date validation is required?`\n+- `### 3.9 — How will the feature be tested on the prepared historical baseline?`\n+- `### 4.5 — Issue 5: Integrate deadline editing into the Administration dashboard`\n+- `## Phase 5 — Documentation and implementation handoff`\n+- `## Cross-cutting concerns`\n+\n+The resolved UI scope is only the Not Routed Cargo table; application and facade operations remain generally callable. Research established the adjacent Destination column as the interaction and styling pattern: use a dynamic dialog, handle `dialogReturn`, and update `tableNotRouted`. The exact tooltip is `Click to change cargo arrival deadline date.` Data is held in the running in-memory sample and survives page reload but resets on rebuild/restart. The mandatory executable evidence is the JDK 17 Open Liberty build/start, HTTP checks, and full `DEF789` browser flow; do not modernize the skipped Payara Arquillian runtime.\n+\n+## Branch and execution order\n+\n+Target `experiment/shepherd-control` from remote `origin`. This is task 5 of 5. The tasks are assigned, completed, and merged serially in plan order. Start only after assignment and tasks 1–4 have passed and merged.\n+\n+Preserve all prior task contracts. Do not inspect, copy, or cherry-pick feature-bearing commits or spike code.\n+\n+## Implement\n+\n+Modify:\n+\n+- `src/main/webapp/admin/tables/listNotRouted.xhtml`\n+\n+In the existing Deadline column, replace the plain text with a `p:commandLink` that:\n+\n+- calls `changeArrivalDeadlineDateDialog.showDialog(cargoNotRouted.trackingId)`;\n+- still displays `cargoNotRouted.arrivalDeadlineDate`;\n+- uses the existing Font Awesome edit-icon styling;\n+- has a stable ID such as `arrivalDeadlineToUpdate`;\n+- listens for `dialogReturn`;\n+- invokes `changeArrivalDeadlineDateDialog.handleReturn`;\n+- updates `tableNotRouted`;\n+- exposes exactly `Click to change cargo arrival deadline date.` as its tooltip.\n+\n+Follow the adjacent Destination column's established structure without changing its behavior, tracking-ID routing, or other cargo tables.\n+\n+Run the complete acceptance flow with `DEF789`: clean build/start on JDK 17; verify home HTTP 200; open Administration; record the deadline; verify icon and tooltip; open the dialog and confirm matching origin/destination; select a visibly different date; Update and verify close plus table refresh; reload and verify persistence; reopen and verify initialization from the changed date; Cancel and verify no further change; verify Destination editing still opens; verify selecting `DEF789` for routing does not error.\n+\n+Inspect the final run logs for the prohibited errors in the plan. Transient JMS activation-order warnings are acceptable only when all endpoints subsequently activate. Update `README.md` only if it already enumerates user-facing Administration capabilities, and then add one concise sentence. Record the exact run command, `DEF789`, before/after dates, and in-memory reset behavior in PR acceptance evidence.\n+\n+## Completion gates\n+\n+- `./mvnw clean package -Popenliberty` succeeds on JDK 17 and all existing/new test sources compile.\n+- The complete browser acceptance flow passes and the changed date survives page reload.\n+- Logs contain no metadata-parent error, `TagException`, `VerifyError`, `FacesException`, `CWWKZ0002E`, `CWWKZ0003E`, recurring batch authorization failure, or new feature-attributable FFDC.\n+- Destination editing, routing selection, tracking, REST, messaging, batch, startup, Derby, Liberty, and prepared compatibility fixes remain intact.\n+- No Java EE namespace migration or unrelated surface change occurs.\n+- Liberty is stopped cleanly.\n+\n+## Out of scope\n+\n+Do not expose the affordance in routed, misrouted, claimed, details, or other tables; alter domain/facade/editor/dialog contracts; change destination editing or routing; add business-date or timezone rules; modernize Arquillian; migrate frameworks or namespaces; replace Derby/Liberty; or redesign unrelated UI and services.\n*** End Patch\n"
````

</details>

```
Added 5 file(s): /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/01-4.1-body.md, /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/02-4.2-body.md, /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/03-4.3-body.md, /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/04-4.4-body.md, /home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies/05-4.5-body.md
```


---

<sub>1m 39s</sub>

### Copilot

All five specifications are persisted with exact reading headings, concrete resolution values, spike/research findings stated as prose, serial prerequisites, discriminating gates, and explicit scope boundaries. I’m running the mandatory disk-based draft validator before initializing the creation ledger or creating issues.

---

<sub>1m 40s</sub>

### `bash`

**Validate all persisted issue drafts**

$ set -euo pipefail
VALIDATOR='/home/edburns/.copilot/plugins/shepherd-task/scripts/validate-stage20-drafts.sh'
BODIES='/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734/issue-bodies'
"$VALIDATOR" "$BODIES" 5 off
for body in "$BODIES"/*-body.md; do
  printf '%s\t%s lines\t%s bytes\n' "$(basename "$body")" "$(wc -l < "$body")" "$(wc -c < "$body")"
done

<details>
<summary>6 lines</summary>

```
01-4.1-body.md	71 lines	4634 bytes
02-4.2-body.md	59 lines	3547 bytes
03-4.3-body.md	66 lines	4260 bytes
04-4.4-body.md	69 lines	4585 bytes
05-4.5-body.md	60 lines	4842 bytes
<shellId: 4 completed with exit code 0>
```

</details>


---

<sub>1m 46s</sub>

### Copilot

The mandatory validator passed for exactly five multiline drafts. Child issues will now be created **without an issue type**, body-verified through REST, and linked one at a time in plan order with an atomically persisted ledger.

---

<sub>2m 11s</sub>

### `bash`

**Create, verify, and link ordered issues**

$ set -u
REPO='edburns/dd-3061974-05-cargotracker-linux'
PARENT=1
LOG='/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734'
BODIES="$LOG/issue-bodies"
LEDGER="$LOG/creation-ledger.json"
RESULT="$LOG/stage-20-result.json"
VERIFIER='/home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh'

atomic_write() {
  local target="$1" content="$2" tmp
  tmp="$(mktemp "$LOG/.atomic.XXXXXX")" || return 1
  printf '%s\n' "$content" > "$tmp" || { rm -f "$tmp"; return 1; }
  mv "$tmp" "$target"
}

update_ledger_flag() {
  local number="$1" field="$2" value="$3" updated
  updated="$(
    jq \
      --argjson number "$number" \
      --arg field "$field" \
      --argjson value "$value" \
      'map(if .number == $number then .[$field] = $value else . end)' \
      "$LEDGER"
  )" || return 1
  atomic_write "$LEDGER" "$updated"
}

reconcile_and_fail() {
  local operation="$1" error="$2" raw normalized updated
  set +e
  raw="$(gh api "repos/$REPO/issues/$PARENT/sub_issues" --paginate --slurp 2>&1)"
  if [ $? -eq 0 ]; then
    normalized="$(printf '%s' "$raw" | jq 'if length == 0 then [] elif all(.[]; type == "array") then add else . end' 2>/dev/null)"
    if [ $? -eq 0 ]; then
      updated="$(jq --argjson children "$normalized" 'map(.linked = ([ $children[].id ] | index(.id) != null))' "$LEDGER" 2>/dev/null)"
      [ $? -ne 0 ] || atomic_write "$LEDGER" "$updated"
    fi
  fi
  atomic_write "$RESULT" "$(jq -n --arg error "$operation: $error" '{schemaVersion:1,status:"failed",ledgerFile:"creation-ledger.json",operationError:$error}')"
  printf 'FAILED OPERATION: %s\nERROR: %s\n' "$operation" "$error" >&2
  if [ "$(jq 'length' "$LEDGER" 2>/dev/null)" = 0 ]; then
    printf 'No issues were created; no cleanup is required.\n' >&2
  else
    jq -r '.[] | "#\(.number)\t\(.title)\t\(.url)\t\(.bodyFile)\tbody_verified=\(.body_verified)\tlinked=\(.linked)"' "$LEDGER" >&2
    jq -r --arg repo "$REPO" '.[] | "gh issue delete \(.number) --repo \"\($repo)\" --yes"' "$LEDGER" >&2
  fi
  printf 'The operation did not complete. No automatic rollback was performed. Delete every issue in the ledger before invoking this skill again.\n' >&2
  exit 1
}

atomic_write "$LEDGER" '[]' || exit 1
atomic_write "$RESULT" '{"schemaVersion":1,"status":"in_progress","ledgerFile":"creation-ledger.json","operationError":null}' || exit 1

subsections=(
  '4.1 — Issue 1: Add the application-layer deadline change operation'
  '4.2 — Issue 2: Expose deadline changes through the booking facade'
  '4.3 — Issue 3: Implement the deadline editor backing model'
  '4.4 — Issue 4: Implement the PrimeFaces deadline dialog'
  '4.5 — Issue 5: Integrate deadline editing into the Administration dashboard'
)
titles=(
  '4.1: Add the application-layer deadline change operation'
  '4.2: Expose deadline changes through the booking facade'
  '4.3: Implement the deadline editor backing model'
  '4.4: Implement the PrimeFaces deadline dialog'
  '4.5: Integrate deadline editing into the Administration dashboard'
)
body_files=(
  "$BODIES/01-4.1-body.md"
  "$BODIES/02-4.2-body.md"
  "$BODIES/03-4.3-body.md"
  "$BODIES/04-4.4-body.md"
  "$BODIES/05-4.5-body.md"
)

for i in 0 1 2 3 4; do
  body="${body_files[$i]}"
  title="${titles[$i]}"
  issue="$(gh api "repos/$REPO/issues" -X POST -f "title=$title" -F "body=@$body" --jq '{id,number,node_id,html_url,title}' 2>&1)"
  rc=$?
  [ $rc -eq 0 ] || reconcile_and_fail 'create issue' "$issue"
  if ! printf '%s' "$issue" | jq -e 'type == "object" and (.id|type)=="number" and (.number|type)=="number"' >/dev/null 2>&1; then
    reconcile_and_fail 'parse create response' "$issue"
  fi
  entry="$(printf '%s' "$issue" | jq --arg subsection "${subsections[$i]}" --arg bodyFile "issue-bodies/$(basename "$body")" '{implementationSubsection:$subsection,bodyFile:$bodyFile,id:.id,number:.number,title:.title,url:.html_url,body_verified:false,linked:false}')" || reconcile_and_fail 'build ledger entry' "issue response for $title"
  updated="$(jq --argjson entry "$entry" '. + [$entry]' "$LEDGER")" || reconcile_and_fail 'append creation ledger' "$title"
  atomic_write "$LEDGER" "$updated" || reconcile_and_fail 'persist creation ledger' "$title"
  number="$(printf '%s' "$issue" | jq -r '.number')"
  issue_id="$(printf '%s' "$issue" | jq -r '.id')"

  verified="$("$VERIFIER" "$REPO" "$number" "$body" 6 5 "$LOG/issue-$number-body-verification-failure.json" 2>&1)"
  rc=$?
  [ $rc -eq 0 ] || reconcile_and_fail 'verify issue body' "$verified"
  update_ledger_flag "$number" body_verified true || reconcile_and_fail 'persist body verification' "issue #$number"

  linked=0
  link_error=''
  for attempt in 1 2 3; do
    link_output="$(printf '{"sub_issue_id": %s}' "$issue_id" | gh api "repos/$REPO/issues/$PARENT/sub_issues" -X POST --input - 2>&1)"
    rc=$?
    if [ $rc -eq 0 ]; then linked=1; break; fi
    link_error="$link_output"
    [ $attempt -eq 3 ] || sleep 2
  done
  [ $linked -eq 1 ] || reconcile_and_fail 'link child issue' "issue #$number: $link_error"
  update_ledger_flag "$number" linked true || reconcile_and_fail 'persist linked status' "issue #$number"
  printf 'created-and-linked #%s %s\n' "$number" "$title"
done

jq . "$LEDGER"


<details>
<summary>58 lines</summary>

```
created-and-linked #2 4.1: Add the application-layer deadline change operation
created-and-linked #3 4.2: Expose deadline changes through the booking facade
created-and-linked #4 4.3: Implement the deadline editor backing model
created-and-linked #5 4.4: Implement the PrimeFaces deadline dialog
created-and-linked #6 4.5: Integrate deadline editing into the Administration dashboard
[
  {
    "implementationSubsection": "4.1 — Issue 1: Add the application-layer deadline change operation",
    "bodyFile": "issue-bodies/01-4.1-body.md",
    "id": 5415420447,
    "number": 2,
    "title": "4.1: Add the application-layer deadline change operation",
    "url": "https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/2",
    "body_verified": true,
    "linked": true
  },
  {
    "implementationSubsection": "4.2 — Issue 2: Expose deadline changes through the booking facade",
    "bodyFile": "issue-bodies/02-4.2-body.md",
    "id": 5415420692,
    "number": 3,
    "title": "4.2: Expose deadline changes through the booking facade",
    "url": "https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/3",
    "body_verified": true,
    "linked": true
  },
  {
    "implementationSubsection": "4.3 — Issue 3: Implement the deadline editor backing model",
    "bodyFile": "issue-bodies/03-4.3-body.md",
    "id": 5415420929,
    "number": 4,
    "title": "4.3: Implement the deadline editor backing model",
    "url": "https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/4",
    "body_verified": true,
    "linked": true
  },
  {
    "implementationSubsection": "4.4 — Issue 4: Implement the PrimeFaces deadline dialog",
    "bodyFile": "issue-bodies/04-4.4-body.md",
    "id": 5415421177,
    "number": 5,
    "title": "4.4: Implement the PrimeFaces deadline dialog",
    "url": "https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/5",
    "body_verified": true,
    "linked": true
  },
  {
    "implementationSubsection": "4.5 — Issue 5: Integrate deadline editing into the Administration dashboard",
    "bodyFile": "issue-bodies/05-4.5-body.md",
    "id": 5415421439,
    "number": 6,
    "title": "4.5: Integrate deadline editing into the Administration dashboard",
    "url": "https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/6",
    "body_verified": true,
    "linked": true
  }
]
<shellId: 5 completed with exit code 0>
```

</details>


---

<sub>2m 17s</sub>

### Copilot

Issues #2–#6 were created, body-verified, and linked successfully. I’m now performing the independent final body checks plus child count, uniqueness, order, open-state, and unassigned-state postconditions before marking the stage complete.

---

<sub>2m 32s</sub>

### `bash`

**Verify final issue postconditions**

$ set -euo pipefail
REPO='edburns/dd-3061974-05-cargotracker-linux'
PARENT=1
LOG='/home/edburns/workareas/dd-3061974-05-cargotracker-linux-shepherd-control/1-arrival-deadline-control-remove-before-merge/prompts/shepherd-task-20-20260910-1734'
LEDGER="$LOG/creation-ledger.json"
BASELINE="$LOG/pre-creation-children.json"
FINAL="$LOG/final-children.json"
RESULT="$LOG/stage-20-result.json"
VERIFIER='/home/edburns/.copilot/plugins/shepherd-task/scripts/verify-github-issue-body.sh'

raw="$(gh api "repos/$REPO/issues/$PARENT/sub_issues" --paginate --slurp)"
printf '%s' "$raw" | jq 'if length == 0 then [] elif all(.[]; type == "array") then add else . end' > "$FINAL"

baseline_count="$(jq 'length' "$BASELINE")"
ledger_count="$(jq 'length' "$LEDGER")"
final_count="$(jq 'length' "$FINAL")"
[ "$final_count" -eq $((baseline_count + ledger_count)) ] || { echo "child count mismatch: baseline=$baseline_count ledger=$ledger_count final=$final_count" >&2; exit 1; }

jq -e --slurpfile children "$FINAL" 'all(.[]; .linked == true and ([ $children[0][] | select(.id == .id) ] | length) >= 0)' "$LEDGER" >/dev/null
jq -n -e --slurpfile ledger "$LEDGER" --slurpfile baseline "$BASELINE" --slurpfile final "$FINAL" '
  ($ledger[0] | map(.id)) as $ledgerIds |
  ($baseline[0] | map(.id)) as $baselineIds |
  ($final[0] | map(select(.id as $id | ($baselineIds | index($id) | not)) | .id)) as $newIds |
  ($newIds == $ledgerIds) and
  (all($ledgerIds[]; . as $id | ([$final[0][] | select(.id == $id)] | length) == 1))
' >/dev/null

expected_subsections='["4.1 — Issue 1: Add the application-layer deadline change operation","4.2 — Issue 2: Expose deadline changes through the booking facade","4.3 — Issue 3: Implement the deadline editor backing model","4.4 — Issue 4: Implement the PrimeFaces deadline dialog","4.5 — Issue 5: Integrate deadline editing into the Administration dashboard"]'
jq -e --argjson expected "$expected_subsections" 'map(.implementationSubsection) == $expected and all(.[]; .body_verified == true and .linked == true)' "$LEDGER" >/dev/null

while IFS=$'\t' read -r number id body_file title; do
  expected="$LOG/$body_file"
  "$VERIFIER" "$REPO" "$number" "$expected" 6 5 "$LOG/issue-$number-final-body-verification-failure.json" >/dev/null
  gh api "repos/$REPO/issues/$number" | jq -e --argjson id "$id" --arg title "$title" '.id == $id and .title == $title and .state == "open" and (.assignees | length) == 0' >/dev/null
done < <(jq -r '.[] | [.number,.id,.bodyFile,.title] | @tsv' "$LEDGER")

tmp="$(mktemp "$LOG/.stage-result.XXXXXX")"
printf '%s\n' '{"schemaVersion":1,"status":"complete","ledgerFile":"creation-ledger.json","operationError":null}' > "$tmp"
mv "$tmp" "$RESULT"

printf 'baseline=%s created=%s final=%s\n' "$baseline_count" "$ledger_count" "$final_count"
jq . "$RESULT"
jq -r '.[] | [.implementationSubsection,(.number|tostring),.title,.url] | @tsv' "$LEDGER"

<details>
<summary>13 lines</summary>

```
baseline=0 created=5 final=5
{
  "schemaVersion": 1,
  "status": "complete",
  "ledgerFile": "creation-ledger.json",
  "operationError": null
}
4.1 — Issue 1: Add the application-layer deadline change operation	2	4.1: Add the application-layer deadline change operation	https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/2
4.2 — Issue 2: Expose deadline changes through the booking facade	3	4.2: Expose deadline changes through the booking facade	https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/3
4.3 — Issue 3: Implement the deadline editor backing model	4	4.3: Implement the deadline editor backing model	https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/4
4.4 — Issue 4: Implement the PrimeFaces deadline dialog	5	4.4: Implement the PrimeFaces deadline dialog	https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/5
4.5 — Issue 5: Integrate deadline editing into the Administration dashboard	6	4.5: Integrate deadline editing into the Administration dashboard	https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/6
<shellId: 6 completed with exit code 0>
```

</details>


---

<sub>2m 39s</sub>

### Copilot

Created and linked five ordered, unassigned child issues under #1. Issues were created **without an issue type**.

| Implementation subsection | Issue | Title |
|---|---:|---|
| 4.1 | [#2](https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/2) | Add the application-layer deadline change operation |
| 4.2 | [#3](https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/3) | Expose deadline changes through the booking facade |
| 4.3 | [#4](https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/4) | Implement the deadline editor backing model |
| 4.4 | [#5](https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/5) | Implement the PrimeFaces deadline dialog |
| 4.5 | [#6](https://github.com/edburns/dd-3061974-05-cargotracker-linux/issues/6) | Integrate deadline editing into the Administration dashboard |

**Ordered child numbers:** `2,3,4,5,6`

**Suggested stage 25 invocation:** invoke `shepherd-task-25-given-list` with ordered issues `2,3,4,5,6` and plan directory `1-arrival-deadline-control-remove-before-merge`. Stage 25 will derive lesson propagation from the campaign manifest.

---

<sub>Generated by [GitHub Copilot CLI](https://github.com/features/copilot/cli)</sub>
