<script lang="ts">
    import { page } from "$app/stores";
    import { derived } from "svelte/store";
    import { goto } from "$app/navigation";
    import ScrollArea from "$lib/components/ui/scroll-area/scroll-area.svelte";
    import * as Table from "$lib/components/ui/table/index.js";
    import PaginationControl from "$lib/components/global/PaginationControl.svelte";
    import ArrowDownIcon from "@lucide/svelte/icons/arrow-down";
    import ArrowUpIcon from "@lucide/svelte/icons/arrow-up";

    import type { IPage } from "$lib/types/auth";
    import type { Infer, SuperValidated } from "sveltekit-superforms";
    import SelectListTab from "../(components)/SelectListTab.svelte";
    import ImportCsvDialog from "../(components)/ImportCsvDialog.svelte";
    import type { SearchMunicipiosSchema } from "./(schema)/searchMunicipiosSchema";
    import type { IMunicipio } from "$lib/types/municipio";
    import SearchMunicipios from "./(components)/SearchMunicipios.svelte";

    type SortKey = "nome" | "codigo" | "populacao";

    let {
        data,
    }: {
        data: {
            municipiosPage: IPage<IMunicipio>;
            form: SuperValidated<Infer<SearchMunicipiosSchema>>;
        };
    } = $props();

    const municipios = $derived(data.municipiosPage.content);
    const totalItems = $derived(data.municipiosPage.page.totalElements);
    const itemsPerPage = $derived(data.municipiosPage.page.size);

    const currentPageStore = derived(
        page,
        ($page) => parseInt($page.url.searchParams.get("page") ?? "0") + 1,
    );

    const currentSortStore = derived(page, ($page) => {
        const sortParam = $page.url.searchParams.get("sort");
        if (!sortParam) return null;
        const [key, direction] = sortParam.split(",");
        return { key: key as SortKey, direction: direction as "asc" | "desc" };
    });

    function handlePageChange(event: CustomEvent<number>) {
        const newPage = event.detail;
        const params = new URLSearchParams($page.url.search);
        params.set("page", (newPage - 1).toString());
        params.set("size", itemsPerPage.toString());
        goto(`?${params.toString()}`);
    }

    function handleSort(key: SortKey) {
        const currentSort = $currentSortStore;
        const params = new URLSearchParams($page.url.search);
        let nextDirection: "asc" | "desc" | null = "asc";

        if (currentSort?.key === key) {
            if (currentSort.direction === "asc") {
                nextDirection = "desc";
            } else {
                // Ciclo: asc -> desc -> sem ordenação
                nextDirection = null;
            }
        }

        if (nextDirection) {
            params.set("sort", `${key},${nextDirection}`);
        } else {
            params.delete("sort");
        }

        // // Resetar para a primeira página ao reordenar
        // params.set("page", "0");

        goto(`?${params.toString()}`, { keepFocus: true, noScroll: true });
    }
</script>

<div class="h-full w-full flex flex-col gap-4">
    <div class="flex items-center justify-between h-max">
        <SelectListTab initialValue="Municipios" />
        <div class="flex items-center gap-2">
            <ImportCsvDialog />
            <SearchMunicipios {data} />
        </div>
    </div>
    <ScrollArea class="h-[90%] w-full">
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head>
                        <button
                            class="flex items-center gap-2 cursor-pointer"
                            onclick={() => handleSort("codigo")}
                        >
                            Código
                            {#if $currentSortStore?.key === "codigo"}
                                {#if $currentSortStore.direction === "asc"}
                                    <ArrowDownIcon class="size-4" />
                                {:else}
                                    <ArrowUpIcon class="size-4" />
                                {/if}
                            {:else}
                                <p>-</p>
                            {/if}
                        </button>
                    </Table.Head>

                    <Table.Head class="w-[120px]">
                        <button
                            class="flex items-center gap-2 cursor-pointer"
                            onclick={() => handleSort("nome")}
                        >
                            Nome
                            {#if $currentSortStore?.key === "nome"}
                                {#if $currentSortStore.direction === "asc"}
                                    <ArrowUpIcon class="size-4" />
                                {:else}
                                    <ArrowDownIcon class="size-4" />
                                {/if}
                            {:else}
                                <p>-</p>
                            {/if}
                        </button>
                    </Table.Head>

                    <Table.Head>Capital</Table.Head>
                    <Table.Head>Estado</Table.Head>
                    <Table.Head class="text-right">
                        <button
                            class="flex items-center justify-end gap-2 cursor-pointer w-full"
                            onclick={() => handleSort("populacao")}
                        >
                            População
                            {#if $currentSortStore?.key === "populacao"}
                                {#if $currentSortStore.direction === "asc"}
                                    <ArrowDownIcon class="size-4" />
                                {:else}
                                    <ArrowUpIcon class="size-4" />
                                {/if}
                            {:else}
                                <p>-</p>
                            {/if}
                        </button>
                    </Table.Head>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                {#each municipios as e}
                    <Table.Row class="hover:bg-primary/20">
                        <Table.Cell class="font-medium">{e.codigo}</Table.Cell>
                        <Table.Cell>{e.nome}</Table.Cell>
                        <Table.Cell>{e.capital}</Table.Cell>
                        <Table.Cell>{e.estado.uf}</Table.Cell>
                        <Table.Cell class="text-right">{e.populacao}</Table.Cell
                        >
                    </Table.Row>
                {/each}
            </Table.Body>
            {#if municipios.length === 0}
                <Table.Caption>Nenhum dado encontrado</Table.Caption>
            {:else}
                <Table.Caption>
                    Mostrando {municipios.length} de {totalItems} municipios encontrados.
                </Table.Caption>
            {/if}
        </Table.Root>
    </ScrollArea>

    <PaginationControl
        {totalItems}
        {itemsPerPage}
        currentPage={$currentPageStore}
        on:pageChange={handlePageChange}
    />
</div>
