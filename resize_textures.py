from PIL import Image
import os

# Source directory with generated images
source_dir = r"C:\Users\itsal\.gemini\antigravity\brain\5d116ce5-b725-4856-8190-fe1863f7a0c1"
# Target directories for mod textures
block_target = r"C:\Projects\UpsideDownMod\src\main\resources\assets\upsidedown\textures\block"
item_target = r"C:\Projects\UpsideDownMod\src\main\resources\assets\upsidedown\textures\item"

# Create directories if they don't exist
os.makedirs(block_target, exist_ok=True)
os.makedirs(item_target, exist_ok=True)

# Mapping of generated image names to final file names
block_textures = {
    "corrupted_grass_top": "corrupted_grass_top.png",
    "corrupted_grass_side": "corrupted_grass_side.png",
    "corrupted_dirt": "corrupted_dirt.png",
    "corrupted_stone": "corrupted_stone.png",
    "dead_grass": "dead_grass.png",
    "dead_flower": "dead_flower.png",
    "dead_leaves": "dead_leaves.png",
    "decayed_log": "decayed_log.png",
    "decayed_log_top": "decayed_log_top.png",
    "decayed_planks": "decayed_planks.png",
    "cracked_glass": "cracked_glass.png",
}

item_textures = {
    "portal_catalyst": "portal_catalyst.png",
}

def resize_image(source_path, target_path, size=(16, 16)):
    """Open image, resize to specified size, and save."""
    try:
        img = Image.open(source_path)
        # Use LANCZOS for high-quality downscaling
        resized = img.resize(size, Image.LANCZOS)
        # Convert to RGBA to preserve transparency
        if resized.mode != 'RGBA':
            resized = resized.convert('RGBA')
        resized.save(target_path, 'PNG')
        print(f"Resized: {os.path.basename(source_path)} -> {target_path}")
    except Exception as e:
        print(f"Error processing {source_path}: {e}")

# Find and process block textures
for name_prefix, target_name in block_textures.items():
    # Find the file (may have timestamp suffix)
    for f in os.listdir(source_dir):
        if f.startswith(name_prefix) and f.endswith('.png'):
            source_path = os.path.join(source_dir, f)
            target_path = os.path.join(block_target, target_name)
            resize_image(source_path, target_path)
            break

# Find and process item textures
for name_prefix, target_name in item_textures.items():
    for f in os.listdir(source_dir):
        if f.startswith(name_prefix) and f.endswith('.png'):
            source_path = os.path.join(source_dir, f)
            target_path = os.path.join(item_target, target_name)
            resize_image(source_path, target_path)
            break

print("\nAll textures processed!")
