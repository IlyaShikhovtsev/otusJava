package ru.shikhovtsev.facade;

import ru.shikhovtsev.facade.codec.Codec;
import ru.shikhovtsev.facade.codec.CodecFactory;
import ru.shikhovtsev.facade.codec.MPEG4CompressionCodec;
import ru.shikhovtsev.facade.codec.OggCompressionCodec;

import java.io.File;

public class VideoConversionFacade {
  public File convertVideo(String fileName, String format) {
    System.out.println("VideoConversionFacade: conversion started.");
    var file = new VideoFile(fileName);
    Codec sourceCodec = CodecFactory.extract(file);
    Codec destinationCodec;
    if (format.equals("mp4")) {
      destinationCodec = new OggCompressionCodec();
    } else {
      destinationCodec = new MPEG4CompressionCodec();
    }
    VideoFile buffer = BitrateReader.read(file, sourceCodec);
    VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
    File result = (new AudioMixer()).fix(intermediateResult);
    System.out.println("VideoConversionFacade: conversion completed");
    return result;
  }
}
