<script lang="ts">
    import { page } from "$app/stores";
    import { derived } from "svelte/store";
    import { goto } from "$app/navigation";
    import ScrollArea from "$lib/components/ui/scroll-area/scroll-area.svelte";
    import * as Table from "$lib/components/ui/table/index.js";
    import PaginationControl from "$lib/components/global/PaginationControl.svelte";
    import ArrowDownIcon from "@lucide/svelte/icons/arrow-down";
    import ArrowUpIcon from "@lucide/svelte/icons/arrow-up";

    import type { IEstado } from "$lib/types/estados";
    import type { IPage } from "$lib/types/auth";

    type SortKey = "uf" | "codigoUf";

    let {
        data,
    }: {
        data: {
            estadosPage: IPage<IEstado>;
        };
    } = $props();

    const estados = $derived(data.estadosPage.content);
    const totalItems = $derived(data.estadosPage.page.totalElements);
    const itemsPerPage = $derived(data.estadosPage.page.size);

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
    <ScrollArea class="h-[90%] w-full">
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head class="w-[120px]">
                        <button
                            class="flex items-center gap-2 cursor-pointer"
                            onclick={() => handleSort("uf")}
                        >
                            UF
                            {#if $currentSortStore?.key === "uf"}
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

                    <Table.Head>
                        <button
                            class="flex items-center gap-2 cursor-pointer"
                            onclick={() => handleSort("codigoUf")}
                        >
                            Código
                            {#if $currentSortStore?.key === "codigoUf"}
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

                    <Table.Head>Município mais populoso - população</Table.Head>
                    <Table.Head class="text-right">População total</Table.Head>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                {#each estados as e}
                    <Table.Row class="hover:bg-primary/20">
                        <Table.Cell class="font-medium">{e.uf}</Table.Cell>
                        <Table.Cell>{e.codigoUf}</Table.Cell>
                        <Table.Cell
                            >{e.municipioMaisPopuloso.nome} - {e
                                .municipioMaisPopuloso.populacao}</Table.Cell
                        >
                        <Table.Cell class="text-right"
                            >{e.populacaoTotal}</Table.Cell
                        >
                    </Table.Row>
                {/each}
            </Table.Body>
        </Table.Root>
    </ScrollArea>

    <PaginationControl
        {totalItems}
        {itemsPerPage}
        currentPage={$currentPageStore}
        on:pageChange={handlePageChange}
    />
</div>
