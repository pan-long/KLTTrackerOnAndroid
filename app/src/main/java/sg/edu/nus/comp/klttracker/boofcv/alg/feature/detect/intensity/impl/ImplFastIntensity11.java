/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sg.edu.nus.comp.klttracker.boofcv.alg.feature.detect.intensity.impl;

import sg.edu.nus.comp.klttracker.boofcv.alg.feature.detect.intensity.FastCornerIntensity;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSingleBand;

/**
 * <p>
 * Contains logic for detecting fast corners. Pixels are sampled such that they can eliminate the most
 * number of possible corners, reducing the number of samples required.
 * </p>
 *
 * <p>
 * DO NOT MODIFY. Generated by {@link GenerateImplFastIntensity}.
 * </p>
 *
 * @author Peter Abeles
 */
public class ImplFastIntensity11<T extends ImageSingleBand> extends FastCornerIntensity<T>
{

	/**
	 * @param helper Provide the image type specific helper.
	 */
	public ImplFastIntensity11(FastHelper<T> helper) {
		super(helper);
	}

	@Override
	protected boolean checkLower( int index )
	{
		if( helper.checkPixelLower(index + offsets[0]) ) {
			if( helper.checkPixelLower(index + offsets[1]) ) {
				if( helper.checkPixelLower(index + offsets[2]) ) {
					if( helper.checkPixelLower(index + offsets[3]) ) {
						if( helper.checkPixelLower(index + offsets[4]) ) {
							if( helper.checkPixelLower(index + offsets[5]) ) {
								if( helper.checkPixelLower(index + offsets[6]) ) {
									if( helper.checkPixelLower(index + offsets[7]) ) {
										if( helper.checkPixelLower(index + offsets[8]) ) {
											if( helper.checkPixelLower(index + offsets[9]) ) {
												if( helper.checkPixelLower(index + offsets[10]) ) {
													return true;
												} else if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else if( helper.checkPixelLower(index + offsets[9]) ) {
							if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else if( helper.checkPixelLower(index + offsets[8]) ) {
						if( helper.checkPixelLower(index + offsets[9]) ) {
							if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[7]) ) {
													if( helper.checkPixelLower(index + offsets[15]) ) {
														return true;
													} else if( helper.checkPixelLower(index + offsets[4]) ) {
														if( helper.checkPixelLower(index + offsets[5]) ) {
															if( helper.checkPixelLower(index + offsets[6]) ) {
																return true;
															} else {
																return false;
															}
														} else {
															return false;
														}
													} else {
														return false;
													}
												} else if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else if( helper.checkPixelLower(index + offsets[7]) ) {
					if( helper.checkPixelLower(index + offsets[8]) ) {
						if( helper.checkPixelLower(index + offsets[9]) ) {
							if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[6]) ) {
												if( helper.checkPixelLower(index + offsets[14]) ) {
													if( helper.checkPixelLower(index + offsets[5]) ) {
														if( helper.checkPixelLower(index + offsets[15]) ) {
															return true;
														} else if( helper.checkPixelLower(index + offsets[4]) ) {
															return true;
														} else {
															return false;
														}
													} else if( helper.checkPixelLower(index + offsets[15]) ) {
														return true;
													} else {
														return false;
													}
												} else if( helper.checkPixelLower(index + offsets[3]) ) {
													if( helper.checkPixelLower(index + offsets[4]) ) {
														if( helper.checkPixelLower(index + offsets[5]) ) {
															return true;
														} else {
															return false;
														}
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if( helper.checkPixelLower(index + offsets[6]) ) {
				if( helper.checkPixelLower(index + offsets[7]) ) {
					if( helper.checkPixelLower(index + offsets[8]) ) {
						if( helper.checkPixelLower(index + offsets[9]) ) {
							if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[5]) ) {
											if( helper.checkPixelLower(index + offsets[13]) ) {
												if( helper.checkPixelLower(index + offsets[4]) ) {
													if( helper.checkPixelLower(index + offsets[14]) ) {
														return true;
													} else if( helper.checkPixelLower(index + offsets[3]) ) {
														return true;
													} else {
														return false;
													}
												} else if( helper.checkPixelLower(index + offsets[14]) ) {
													if( helper.checkPixelLower(index + offsets[15]) ) {
														return true;
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else if( helper.checkPixelLower(index + offsets[2]) ) {
												if( helper.checkPixelLower(index + offsets[3]) ) {
													if( helper.checkPixelLower(index + offsets[4]) ) {
														return true;
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if( helper.checkPixelLower(index + offsets[5]) ) {
			if( helper.checkPixelLower(index + offsets[6]) ) {
				if( helper.checkPixelLower(index + offsets[7]) ) {
					if( helper.checkPixelLower(index + offsets[8]) ) {
						if( helper.checkPixelLower(index + offsets[9]) ) {
							if( helper.checkPixelLower(index + offsets[10]) ) {
								if( helper.checkPixelLower(index + offsets[11]) ) {
									if( helper.checkPixelLower(index + offsets[4]) ) {
										if( helper.checkPixelLower(index + offsets[12]) ) {
											if( helper.checkPixelLower(index + offsets[3]) ) {
												if( helper.checkPixelLower(index + offsets[13]) ) {
													return true;
												} else if( helper.checkPixelLower(index + offsets[2]) ) {
													return true;
												} else {
													return false;
												}
											} else if( helper.checkPixelLower(index + offsets[13]) ) {
												if( helper.checkPixelLower(index + offsets[14]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelLower(index + offsets[1]) ) {
											if( helper.checkPixelLower(index + offsets[2]) ) {
												if( helper.checkPixelLower(index + offsets[3]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else if( helper.checkPixelLower(index + offsets[12]) ) {
										if( helper.checkPixelLower(index + offsets[13]) ) {
											if( helper.checkPixelLower(index + offsets[14]) ) {
												if( helper.checkPixelLower(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean checkUpper( int index )
	{
		if( helper.checkPixelUpper(index + offsets[0]) ) {
			if( helper.checkPixelUpper(index + offsets[1]) ) {
				if( helper.checkPixelUpper(index + offsets[2]) ) {
					if( helper.checkPixelUpper(index + offsets[3]) ) {
						if( helper.checkPixelUpper(index + offsets[4]) ) {
							if( helper.checkPixelUpper(index + offsets[5]) ) {
								if( helper.checkPixelUpper(index + offsets[6]) ) {
									if( helper.checkPixelUpper(index + offsets[7]) ) {
										if( helper.checkPixelUpper(index + offsets[8]) ) {
											if( helper.checkPixelUpper(index + offsets[9]) ) {
												if( helper.checkPixelUpper(index + offsets[10]) ) {
													return true;
												} else if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else if( helper.checkPixelUpper(index + offsets[9]) ) {
							if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else if( helper.checkPixelUpper(index + offsets[8]) ) {
						if( helper.checkPixelUpper(index + offsets[9]) ) {
							if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[7]) ) {
													if( helper.checkPixelUpper(index + offsets[15]) ) {
														return true;
													} else if( helper.checkPixelUpper(index + offsets[4]) ) {
														if( helper.checkPixelUpper(index + offsets[5]) ) {
															if( helper.checkPixelUpper(index + offsets[6]) ) {
																return true;
															} else {
																return false;
															}
														} else {
															return false;
														}
													} else {
														return false;
													}
												} else if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else if( helper.checkPixelUpper(index + offsets[7]) ) {
					if( helper.checkPixelUpper(index + offsets[8]) ) {
						if( helper.checkPixelUpper(index + offsets[9]) ) {
							if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[6]) ) {
												if( helper.checkPixelUpper(index + offsets[14]) ) {
													if( helper.checkPixelUpper(index + offsets[5]) ) {
														if( helper.checkPixelUpper(index + offsets[15]) ) {
															return true;
														} else if( helper.checkPixelUpper(index + offsets[4]) ) {
															return true;
														} else {
															return false;
														}
													} else if( helper.checkPixelUpper(index + offsets[15]) ) {
														return true;
													} else {
														return false;
													}
												} else if( helper.checkPixelUpper(index + offsets[3]) ) {
													if( helper.checkPixelUpper(index + offsets[4]) ) {
														if( helper.checkPixelUpper(index + offsets[5]) ) {
															return true;
														} else {
															return false;
														}
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if( helper.checkPixelUpper(index + offsets[6]) ) {
				if( helper.checkPixelUpper(index + offsets[7]) ) {
					if( helper.checkPixelUpper(index + offsets[8]) ) {
						if( helper.checkPixelUpper(index + offsets[9]) ) {
							if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[5]) ) {
											if( helper.checkPixelUpper(index + offsets[13]) ) {
												if( helper.checkPixelUpper(index + offsets[4]) ) {
													if( helper.checkPixelUpper(index + offsets[14]) ) {
														return true;
													} else if( helper.checkPixelUpper(index + offsets[3]) ) {
														return true;
													} else {
														return false;
													}
												} else if( helper.checkPixelUpper(index + offsets[14]) ) {
													if( helper.checkPixelUpper(index + offsets[15]) ) {
														return true;
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else if( helper.checkPixelUpper(index + offsets[2]) ) {
												if( helper.checkPixelUpper(index + offsets[3]) ) {
													if( helper.checkPixelUpper(index + offsets[4]) ) {
														return true;
													} else {
														return false;
													}
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if( helper.checkPixelUpper(index + offsets[5]) ) {
			if( helper.checkPixelUpper(index + offsets[6]) ) {
				if( helper.checkPixelUpper(index + offsets[7]) ) {
					if( helper.checkPixelUpper(index + offsets[8]) ) {
						if( helper.checkPixelUpper(index + offsets[9]) ) {
							if( helper.checkPixelUpper(index + offsets[10]) ) {
								if( helper.checkPixelUpper(index + offsets[11]) ) {
									if( helper.checkPixelUpper(index + offsets[4]) ) {
										if( helper.checkPixelUpper(index + offsets[12]) ) {
											if( helper.checkPixelUpper(index + offsets[3]) ) {
												if( helper.checkPixelUpper(index + offsets[13]) ) {
													return true;
												} else if( helper.checkPixelUpper(index + offsets[2]) ) {
													return true;
												} else {
													return false;
												}
											} else if( helper.checkPixelUpper(index + offsets[13]) ) {
												if( helper.checkPixelUpper(index + offsets[14]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else if( helper.checkPixelUpper(index + offsets[1]) ) {
											if( helper.checkPixelUpper(index + offsets[2]) ) {
												if( helper.checkPixelUpper(index + offsets[3]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else if( helper.checkPixelUpper(index + offsets[12]) ) {
										if( helper.checkPixelUpper(index + offsets[13]) ) {
											if( helper.checkPixelUpper(index + offsets[14]) ) {
												if( helper.checkPixelUpper(index + offsets[15]) ) {
													return true;
												} else {
													return false;
												}
											} else {
												return false;
											}
										} else {
											return false;
										}
									} else {
										return false;
									}
								} else {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
