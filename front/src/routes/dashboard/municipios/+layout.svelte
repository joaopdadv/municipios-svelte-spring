<script lang="ts">
    import Button from "$lib/components/ui/button/button.svelte";
    import ImportCsvDialog from "../(components)/ImportCsvDialog.svelte";
    import SelectListTab from "../(components)/SelectListTab.svelte";
    import "../../../app.css";

    let { children } = $props();
</script>

<div class="flex-1 w-full flex flex-col gap-4 min-h-0">
    <div class="flex items-center justify-between h-max">
        <SelectListTab initialValue="Municipios" />
        <div class="flex items-center gap-2">
            <ImportCsvDialog />
            <Button variant="default">Pesquisar</Button>
        </div>
    </div>
    <div class="flex-1 w-full min-h-0">
        {@render children()}
    </div>
</div>
