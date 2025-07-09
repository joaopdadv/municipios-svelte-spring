<script lang="ts">
    import { goto } from "$app/navigation";
    import Menu from "$lib/components/global/home/Menu.svelte";
    import Button from "$lib/components/ui/button/button.svelte";
    import "../../app.css";
    import SelectListTab from "./(components)/SelectListTab.svelte";

    let { children } = $props();

    let selected = $state({
        value: "Estados",
        options: ["Estados", "Municipios"],
    });

    function handleSelectChange(select: string) {
        if (selected.options.includes(select)) {
            selected.value = select;
            goto(`/dashboard/${select.toLowerCase()}`);
        }
    }
</script>

<div
    class="w-screen h-screen bg-background flex flex-col items-center justify-start p-6 gap-6"
>
    <Menu />
    {@render children()}
</div>
