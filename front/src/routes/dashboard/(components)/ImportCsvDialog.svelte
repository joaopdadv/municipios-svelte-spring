<script lang="ts">
    import { buttonVariants } from "$lib/components/ui/button/index.js";
    import * as Dialog from "$lib/components/ui/dialog/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import { defaults, superForm } from "sveltekit-superforms";
    import { zod4 } from "sveltekit-superforms/adapters";
    import { toast } from "svelte-sonner";
    import * as Form from "$lib/components/ui/form/index.js";

    import { z } from "zod/v4";
    import { uploadCsv } from "$lib/api/csv/csvApi";

    let isLoading = $state(false);
    let isOpen = $state(false);

    const formSchema = z.object({
        file: z
            .instanceof(File, { message: "Você deve selecionar um arquivo." })
            .refine((file) => file.size > 0, "Arquivo não pode estar vazio."),
    });

    const form = superForm(defaults(zod4(formSchema)), {
        validators: zod4(formSchema),
        SPA: true,
        onUpdate: async ({ form: f }) => {
            if (f.valid) {
                isLoading = true;
                await uploadCsv(f.data.file, null);
                isLoading = false;
                isOpen = false;
                toast.success(`Arquivo enviado: ${f.data.file.name}`);
            } else {
                toast.error("Por favor selecione um arquivo válido.");
            }
        },
    });

    const { form: formData, enhance } = form;
</script>

<Dialog.Root open={isOpen} onOpenChange={(open) => (isOpen = open)}>
    <Dialog.Trigger class={buttonVariants({ variant: "outline" })}>
        Importar CSV
    </Dialog.Trigger>
    <Dialog.Content class="sm:max-w-[425px]">
        <Dialog.Header>
            <Dialog.Title>Importar arquivo</Dialog.Title>
            <Dialog.Description>
                Selecione um arquivo CSV para enviar.
            </Dialog.Description>
        </Dialog.Header>

        <form
            method="POST"
            class=" space-y-6"
            enctype="multipart/form-data"
            use:enhance
        >
            <Form.Field {form} name="file" class="w-full">
                <Form.Control>
                    {#snippet children({
                        props,
                    }: {
                        props: Record<string, any>;
                    })}
                        <Form.Label>Arquivo CSV</Form.Label>
                        <Input
                            class="w-full"
                            type="file"
                            accept=".csv"
                            onchange={(e) => {
                                const file = (e.target as HTMLInputElement)
                                    ?.files?.[0];
                                if (file) $formData.file = file;
                            }}
                        />
                    {/snippet}
                </Form.Control>
                <Form.Description>
                    O arquivo deve estar no formato CSV.
                </Form.Description>
                <Form.FieldErrors />
            </Form.Field>
            <Form.Button disabled={isLoading}>Enviar</Form.Button>
        </form>
    </Dialog.Content>
</Dialog.Root>
