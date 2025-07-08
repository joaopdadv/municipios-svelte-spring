<script lang="ts">
    import { goto } from "$app/navigation";
    import Menu from "$lib/components/global/home/Menu.svelte";
    import Button from "$lib/components/ui/button/button.svelte";
    import "../../app.css";

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
    <div class="flex-1 w-full flex flex-col gap-4 min-h-0">
        <div class="flex items-center justify-between h-max">
            <div class="flex items-center gap-2 text-2xl font-medium">
                <button
                    onclick={() => handleSelectChange("Estados")}
                    class={`${selected.value == "Estados" ? "text-black" : "text-gray-300"} cursor-pointer transition`}
                >
                    Estados
                </button>
                <button
                    onclick={() => handleSelectChange("Municipios")}
                    class={`${selected.value == "Municipios" ? "text-black" : "text-gray-300"} cursor-pointer transition`}
                >
                    Municipios
                </button>
            </div>
            <div class="flex items-center gap-2">
                <Button variant="outline">Importar CSV</Button>
                <Button variant="default">Pesquisar</Button>
            </div>
        </div>
        <div class="flex-1 w-full min-h-0">
            {@render children()}
        </div>
    </div>
</div>
