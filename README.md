# blayer
Play video in 20FPS using blocks in minecraft

blayer uses [jcodec](https://github.com/jcodec/jcodec) for capturing frames.    
(it is `BSD-2-Clause license`, Thanks to jcodec!)


<img src="https://github.com/sbkimxtheia/blayer/blob/master/preview.gif" width="480" height="270" alt="">


## Supported Versions

blayer only supports [PaperMC/Paper](https://github.com/PaperMC/Paper) for now.  
blayer supports minecraft `1.19.x`.

## Dependencies

[ink](https://github.com/XTHEIA/ink) is required as plugin.  
but it is not public, so `ink-1.0.jar` is included in release file.

## Usage

### 1. Place Video File

Place video files(.mp4) in `<server>/plugins/Blayer/videos` folder.   
If the video file's resolution is `160x90`, blayer will play it with `160x90` blocks in a frame.   
You should adjust the resolution of video files. Proper resolution is 160x90.   
If you placed video files correctly, you can see the name of files in command suggestion in minecraft.  
<img src="https://github.com/sbkimxtheia/blayer/blob/master/1.PNG" width="250" height="125">

### 2. Mapping Colors

To play a video file, two steps are required. First is mapping colors of video frames.  
You must run : `/blayer <filename> 1_map_color <duration>`  (`1_map_color` will be suggested in minecraft chat.)  
ex) If name of the video file is `bad_apple.mp4` and it's duration is 3 minutes and 39 seconds length,    
the command will be   
`/blayer bad_apple.mp4 1_map_color 219`  
After the command, color mapping will be processed like this :   
<img src="https://github.com/sbkimxtheia/blayer/blob/master/2.PNG" width="600" height="290">   
Mapped color data will be saved in `<filename>_colors.txt`.     
File's size will be very big if you used high resolution video.

### 3. Mapping Blocks

It is the second step of mapping, very similar with `mapping colors`.   
You must run : `/blayer <filename> 2_map_blocks` (duration not required)
Processing is very similar with step 2. (`<filename>_blocks.txt` will be created.)

### 4. Play!

You can play video now.   
Run : `/blayer <filename> 3_play`

#### THE VIDEO WILL BE ONLY VISIBLE TO THE PLAYER WHO USED THE COMMAND   
#### (because it sends block change packets to player(client-side))

The top left pixel of the frame is placed under the player.

   
