<script lang="ts">
    import { goto } from "$app/navigation";

    const { initialValue = "Estados" } = $props<{
        initialValue?: "Estados" | "Municipios";
    }>();

    let selected = $state({
        value: initialValue,
        options: ["Estados", "Municipios"],
    });

    function handleSelectChange(select: string) {
        if (selected.options.includes(select)) {
            selected.value = select;
            goto(`/dashboard/${select.toLowerCase()}`);
        }
    }
</script>

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
