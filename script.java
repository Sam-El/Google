// 1. Setup variables
const connectBtn = document.querySelector('.btn-connect');
let provider;
let signer;

async function connectWallet() {
    // Check if the browser has a wallet injected (window.ethereum)
    if (window.ethereum) {
        try {
            // 2. Initialize the Provider (The "Read" connection)
            provider = new ethers.BrowserProvider(window.ethereum);

            // 3. Request account access from the user
            const accounts = await provider.send("eth_requestAccounts", []);
            
            // 4. Get the Signer (The "Write" connection - allows for transactions)
            signer = await provider.getSigner();
            const address = await signer.getAddress();

            // 5. Update UI: Show a shortened version of the address (e.g., 0x123...456)
            connectBtn.innerText = `${address.substring(0, 6)}...${address.substring(address.length - 4)}`;
            connectBtn.classList.add('connected');
            
            console.log("Connected to:", address);
        } catch (error) {
            console.error("User denied account access or error occurred:", error);
        }
    } else {
        alert("Please install a Web3 wallet like MetaMask to use this feature!");
    }
}
// Register the plugin
gsap.registerPlugin(ScrollTrigger);

// 1. Animate the Roadmap Line "Drawing"
gsap.to(".roadmap-line", {
    height: "100%",
    ease: "none",
    scrollTrigger: {
        trigger: ".roadmap-container",
        start: "top center", // Start when top of section hits center of screen
        end: "bottom center",
        scrub: true, // Link animation to scroll bar
    }
});

// 2. Animate each Step "Popping" In
gsap.utils.toArray(".roadmap-step").forEach((step) => {
    gsap.to(step, {
        opacity: 1,
        y: 0,
        duration: 1,
        scrollTrigger: {
            trigger: step,
            start: "top 80%", // Trigger when step is 80% down the screen
            toggleActions: "play none none reverse", 
        }
    });
});

// Attach the function to your button
connectBtn.addEventListener('click', connectWallet);