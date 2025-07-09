<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import * as Pagination from "$lib/components/ui/pagination/index.js";
    import ChevronLeftIcon from "@lucide/svelte/icons/chevron-left";
    import { getPages } from "$lib/utils";
    import { MediaQuery } from "svelte/reactivity";

    type Props = {
        totalItems: number;
        itemsPerPage: number;
        currentPage: number; // Espera a página em base 1
    };

    let { totalItems, itemsPerPage, currentPage }: Props = $props();

    const dispatch = createEventDispatcher<{ pageChange: number }>();

    const totalPages = $derived(Math.ceil(totalItems / itemsPerPage));

    const isDesktop = new MediaQuery("(min-width: 768px)");
    const siblingCount = $derived(isDesktop.current ? 1 : 0);

    function onPageChange(newPage: number) {
        if (newPage >= 1 && newPage <= totalPages && newPage !== currentPage) {
            dispatch("pageChange", newPage);
        }
    }
</script>

<Pagination.Root
    count={totalItems}
    perPage={itemsPerPage}
    page={currentPage}
    {siblingCount}
>
    <Pagination.Content>
        <Pagination.Item>
            <Pagination.PrevButton
                onclick={() => onPageChange(currentPage - 1)}
            >
                <ChevronLeftIcon class="size-4" />
                <span class="hidden sm:block">Anterior</span>
            </Pagination.PrevButton>
        </Pagination.Item>

        {#each getPages( { totalItems, itemsPerPage, currentPage, siblingCount }, ) as page (page.key)}
            {#if page.type === "ellipsis"}
                <Pagination.Item>
                    <Pagination.Ellipsis />
                </Pagination.Item>
            {:else}
                <Pagination.Item>
                    <Pagination.Link
                        {page}
                        isActive={currentPage === page.value}
                        onclick={() => onPageChange(page.value)}
                    >
                        {page.value}
                    </Pagination.Link>
                </Pagination.Item>
            {/if}
        {/each}

        <Pagination.Item>
            <Pagination.NextButton
                onclick={() => onPageChange(currentPage + 1)}
            >
                <span class="hidden sm:block">Próximo</span>
                <ChevronLeftIcon class="size-4 rotate-180" />
            </Pagination.NextButton>
        </Pagination.Item>
    </Pagination.Content>
</Pagination.Root>
