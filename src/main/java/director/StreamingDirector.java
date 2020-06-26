package director;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class StreamingDirector {
	
	private static Logger log = Logger.getLogger(StreamingDirector.class.getName());
	
	private static FFmpeg ffmpeg;
	
	private static FFprobe ffprobe;
	
	public static void videoProcess(File file, long bitRate, String format) {
		
		String inputDir = "C:\\ffmpeg\\bin\\raw_videos\\";
		String outDir = "C:\\ffmpeg\\bin\\videos\\";
		String output = outDir + file.getName().substring(0, file.getName().length() - 4) + "-" + 
				  (Double.valueOf(bitRate) / Double.valueOf(1000000)) + "Mbps." + format;
		
		initialiseFFMpegClient();
		
		log.info("Creating the transcoding");

		FFmpegBuilder builder = new FFmpegBuilder()
				.overrideOutputFiles(true)
				.setInput(inputDir + file.getName())
				.addOutput(output)
				.setVideoBitRate(bitRate)
				.done();
		
		log.info("Creating the executor");
		
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		
		log.info("Starting the transcoding for file " + file.getName());
		System.out.println(outDir + file.getName().substring(0, file.getName().length() - 4) + "-" + 
				  (Double.valueOf(bitRate) / Double.valueOf(1000000)) + "Mbps." + format);
		// Run a one-pass encode
		executor.createJob(builder).run();
				
		log.info("Transcoding finished");
	}
	
	private static void initialiseFFMpegClient() {
		
		try {
			log.info("Initialising FFMpegClient");
			
			ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");
			ffprobe = new FFprobe("C:\\ffmpeg\\bin\\ffprobe.exe");
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
