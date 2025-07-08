<script lang="ts">
    import { page } from "$app/stores";
    import { derived } from "svelte/store";
    import { goto } from "$app/navigation";
    import ScrollArea from "$lib/components/ui/scroll-area/scroll-area.svelte";
    import * as Table from "$lib/components/ui/table/index.js";
    import { MediaQuery } from "svelte/reactivity";

    import type { IEstado } from "$lib/types/estados";
    import type { IPage } from "$lib/types/auth";
    import PaginationControl from "$lib/components/global/PaginationControl.svelte";

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

    function handlePageChange(event: CustomEvent<number>) {
        const newPage = event.detail;
        goto(`?page=${newPage - 1}&size=${itemsPerPage}`);
    }
</script>

<div class="h-full w-full flex flex-col gap-4">
    <ScrollArea class="h-[90%] w-full">
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head class="w-[100px]">UF</Table.Head>
                    <Table.Head>Código</Table.Head>
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
