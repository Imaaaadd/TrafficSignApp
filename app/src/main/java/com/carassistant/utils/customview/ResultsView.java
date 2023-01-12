package com.carassistant.utils.customview;

import java.util.List;
import com.carassistant.tflite.detection.Classifier.Recognition;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
