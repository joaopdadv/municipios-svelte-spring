<script>
	import { Toaster } from "svelte-sonner";
	import "../app.css";

	let { children } = $props();
</script>

<Toaster position="top-center" />
{@render children()}
