<script lang="ts">
    import ChevronLeftIcon from "@lucide/svelte/icons/chevron-left";
    import ChevronRightIcon from "@lucide/svelte/icons/chevron-right";
    import * as Pagination from "$lib/components/ui/pagination/index.js";
    import { paginationStore } from "$lib/store/pagination.store";

    export let totalItems: number;
    export let itemsPerPage: number;

    // se totalItems ou itemsPerPage forem alterados, atualiza a store
    $: {
        paginationStore.setItems(totalItems, itemsPerPage);
    }

    const { setPage, nextPage, prevPage } = paginationStore;
</script>

<Pagination.Root
    count={totalItems}
    perPage={itemsPerPage}
    page={$paginationStore.currentPage}
>
    {#snippet children({ pages })}
        <Pagination.Content>
            <Pagination.Item>
                <Pagination.PrevButton onclick={prevPage}>
                    <ChevronLeftIcon class="size-4" />
                    <span class="hidden sm:block">Anterior</span>
                </Pagination.PrevButton>
            </Pagination.Item>

            {#each pages as page (page.key)}
                {#if page.type === "ellipsis"}
                    <Pagination.Item>
                        <Pagination.Ellipsis />
                    </Pagination.Item>
                {:else}
                    <Pagination.Item>
                        <Pagination.Link
                            {page}
                            isActive={$paginationStore.currentPage ===
                                page.value}
                            onclick={() => setPage(page.value)}
                        >
                            {page.value}
                        </Pagination.Link>
                    </Pagination.Item>
                {/if}
            {/each}

            <Pagination.Item>
                <Pagination.NextButton onclick={nextPage}>
                    <span class="hidden sm:block">Próximo</span>
                    <ChevronRightIcon class="size-4" />
                </Pagination.NextButton>
            </Pagination.Item>
        </Pagination.Content>
    {/snippet}
</Pagination.Root>
