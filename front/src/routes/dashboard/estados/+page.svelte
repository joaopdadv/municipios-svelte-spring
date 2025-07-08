<script lang="ts">
    import ScrollArea from "$lib/components/ui/scroll-area/scroll-area.svelte";
    import * as Table from "$lib/components/ui/table/index.js";

    import PaginationComponent from "$lib/components/global/pagination/PaginationComponent.svelte";
    import type { PageData } from "../$types";

    let { data }: { data: { estados: Array<string> } } = $props();
    console.log("Data received in estados page:", data.estados);
</script>

<div class="h-full w-full flex flex-col gap-4">
    <ScrollArea class="h-[90%] w-full">
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head class="w-[100px]">Invoice</Table.Head>
                    <Table.Head>Status</Table.Head>
                    <Table.Head>Method</Table.Head>
                    <Table.Head class="text-right">Amount</Table.Head>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                <!-- {#each invoices as invoice (invoice.invoice)}
                    <Table.Row>
                        <Table.Cell class="font-medium"
                            >{invoice.invoice}</Table.Cell
                        >
                        <Table.Cell>{invoice.paymentStatus}</Table.Cell>
                        <Table.Cell>{invoice.paymentMethod}</Table.Cell>
                        <Table.Cell class="text-right"
                            >{invoice.totalAmount}</Table.Cell
                        >
                    </Table.Row>
                {/each} -->
            </Table.Body>
        </Table.Root>
    </ScrollArea>

    <PaginationComponent totalItems={200} itemsPerPage={20} />
</div>
