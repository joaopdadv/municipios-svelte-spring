<script lang="ts">
    import type { PageData } from "./$types";
    import ImportCsvDialog from "../(components)/ImportCsvDialog.svelte";
    import SelectListTab from "../(components)/SelectListTab.svelte";
    import "../../../app.css";
    import SearchEstados from "./(components)/SearchEstados.svelte";

    // let { children } = $props();
    // let { data }: { data: PageData } = $props();
    let { children }: { children: any } = $props();
</script>

<div class="flex-1 w-full flex flex-col gap-4 min-h-0">
    <div class="flex-1 w-full min-h-0">
        {@render children()}
    </div>
</div>
